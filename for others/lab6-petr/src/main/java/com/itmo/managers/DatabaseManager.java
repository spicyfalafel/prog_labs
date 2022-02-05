package com.itmo.managers;


import com.itmo.product.*;
import com.itmo.product.eyes.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class DatabaseManager {
    //private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static final String FILE_WITH_ACCOUNT = "account";
    private static final String USERS_TABLE = "users";
    private static final String pepper = "1@#$&^%$)3";

    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
             BufferedReader reader = new BufferedReader(fileReader)) {
            USER = reader.readLine();
            PASS = reader.readLine();
            DB_URL = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PassEncoder passEncoder;

    public DatabaseManager(String dbUrl, String user, String pass) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            passEncoder = new PassEncoder(pepper);
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }

    public DatabaseManager(String address, int port, String dbName, String user, String pass) {
        this("jdbc:postgresql://" + address + ":" + port + "/" + dbName, user, pass);
    }

    public DatabaseManager() {
        this(DB_URL, USER, PASS);
    }

    //загрузка коллекции в память
    public HashTableManager getCollectionFromDatabase() {
        HashTableManager ht = new HashTableManager();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("" +
                    "SELECT *\n" +
                    "FROM PRODUCT P \n" +
                    "LEFT JOIN COORDINATE C ON (P.PRODUCT_ID = C.PRODUCT_ID)\n" +
                    "LEFT JOIN PERSON PP ON (PP.PRODUCT_ID = P.PRODUCT_ID) \n" +
                    "LEFT JOIN LOCATIONS L ON (PP.PERSON_ID = L.PERSON_ID)\n" +
                    "LEFT JOIN USERS U ON (P.OWNER_ID = U.USERS_ID);");


            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String prodName = rs.getString("product_name");
                User owner = new User(rs.getString("user_name"), rs.getString("hash"));
                LocalDateTime creationDate = DateTimeAdapter.convertToLocalDateTimeViaMilisecond(
                        rs.getDate("creation_date"));
                Integer price = rs.getInt("price");
                Long prodPrice = price == 0 ? null : (Long) price.longValue();

                Long manuCost = ((Integer) rs.getInt("manufacture_cost")).longValue();
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(rs.getString("unit_of_measure"));

                Coordinates coordinates = new Coordinates(rs.getInt("coordinate_x"),
                        rs.getInt("coordinate_y"));

                String personName = rs.getString("person_name");
                java.time.ZonedDateTime birthday = rs.getTimestamp("birthday").toLocalDateTime()
                        .atZone(ZoneId.of("Europe/Moscow"));
                Color eyeColor = Color.valueOf(rs.getString("eye_color"));
                String hairColor = rs.getString("hair_color");
                com.itmo.product.hair.Color hair = hairColor != null ? com.itmo.product.hair.Color.valueOf(hairColor) : null;
                Country nationality = Country.valueOf(rs.getString("nationality"));

                int locId = rs.getInt("location_id");
                Location loc = null;
                if (locId != 0) {
                    float locX = rs.getFloat("loc_x");
                    long locY = rs.getInt("loc_y");
                    Double locZ = rs.getDouble("loc_z");
                    String name = rs.getString("loc_name");
                    loc = new Location(locX, locY, locZ, name);
                }

                Person person = new Person(personName, birthday, eyeColor, hair, nationality, loc);

                Product product = new Product(id, owner, prodName, coordinates, creationDate, prodPrice,
                        manuCost, unitOfMeasure, person);
                ht.insert(product.getId(), product);
            }
            return ht;
        } catch (SQLException e) {
            e.printStackTrace();
            return ht;
        }
    }

    public void syncWithHt(HashTableManager ht, String username) {
        removeAll(username);
        for (Product p : ht.getProducts().values()) {
            addElement(p);
        }
    }

    //добаление нового элемента
    public boolean addElement(Product prod) {
        try {

            String prodInsert = prod.getId() > 0 ? "insert into product (owner_id, product_name, creation_date, price, manufacture_cost," +
                    " unit_of_measure, product_id)" +
                    " values (?, ?, ?, ?, ?, ?, ?)" : "insert into product (owner_id, product_name, creation_date, price, manufacture_cost, unit_of_measure)" +
                    " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement productStatement = connection.prepareStatement(prodInsert, Statement.RETURN_GENERATED_KEYS);

            productStatement.setInt(1, userId(prod.getUserOwner().getName()));
            productStatement.setString(2, prod.getName());
            productStatement.setDate(3, java.sql.Date.valueOf(prod.getCreationDate().toLocalDate()));
            productStatement.setInt(4, prod.getPrice().intValue());
            productStatement.setInt(5, prod.getManufactureCost().intValue());
            productStatement.setString(6, prod.getUnitOfMeasure().toString());

            if (prod.getId() > 0) {
                productStatement.setInt(7, prod.getId());
            }

            productStatement.executeUpdate();
            ResultSet rs = productStatement.getGeneratedKeys();
            if (rs.next()) {
                prod.setId(rs.getInt(1));
            }
            //System.out.println("inserted id is " + prod.getId());

            PreparedStatement statementCoord = connection.prepareStatement(
                    "insert into coordinate(coordinate_x, coordinate_y, product_id) " +
                            "values (?, ?, ?)");
            Coordinates c = prod.getCoordinates();
            statementCoord.setInt(1, c.getX());
            statementCoord.setInt(2, c.getY());
            statementCoord.setInt(3, prod.getId());
            statementCoord.executeUpdate();


            Person p = prod.getOwner();
            PreparedStatement statementPers = connection.prepareStatement(
                    "insert into person(person_name, birthday, eye_color, hair_color, nationality, product_id)\n" +
                            "values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statementPers.setString(1, p.getName());
            statementPers.setTimestamp(2, Timestamp.valueOf(p.getBirthday().toLocalDateTime()));
            statementPers.setString(3, p.getEyeColor().toString());
            statementPers.setString(4, p.getHairColor().toString());
            statementPers.setString(5, p.getNationality().toString());
            statementPers.setInt(6, prod.getId());
            statementPers.executeUpdate();

            ResultSet rs2 = statementPers.getGeneratedKeys();
            int personId = -1;
            if (rs2.next()) {
                personId = rs2.getInt(1);
                //System.out.println("inserted id of person is " + personId);
            }


            Location l = p.getLocation();
            if (l != null){
                PreparedStatement statementLoc = connection.prepareStatement(
                        "insert into locations(loc_x, loc_y, loc_z, loc_name, person_id) " +
                                "values (?, ?, ?, ?, ?)");
                statementLoc.setFloat(1, l.getX());
                statementLoc.setInt(2, (int) l.getY());
                statementLoc.setDouble(3, l.getZ());
                statementLoc.setString(4, l.getName());
                statementLoc.setInt(5, personId);
                statementLoc.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //удаление элемента по id
    public int remove(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from PRODUCT where product_id=?");
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //добавление нового пользователя
    public void addUser(User user) {
        String salt = new SimplePasswordGenerator(true, true, true, true).generate(10, 10);
        String hash = passEncoder.getHash(user.getPass() + salt);
        try {
            PreparedStatement statement = connection.prepareStatement("insert into " + USERS_TABLE + "" +
                    "(user_name, hash, salt) values (?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ищем пользователя
    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where user_name = ?");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString("salt");
            String hash = passEncoder.getHash(user.getPass() + salt);
            statement = connection.prepareStatement("select * from " + USERS_TABLE +
                    " where user_name = ? and hash = ? and salt=?");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int userId(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select users_id from " + USERS_TABLE + " where user_name = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return 0;
            else {
                return resultSet.getInt("users_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //ищем пользователя только по имени
    public boolean containsUserName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where user_name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //удаляем все элементы, принадлежащие пользователю
    public boolean removeAll(String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from PRODUCT " +
                    "where owner_id=(select users_id from users where user_name = ?)");
            statement.setString(1, userName);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //обновляем поля элемента
    public int update(long id, Product product) {
        return 0;
    }
}
