package com.itmo.utils;

import com.itmo.client.User;
import com.itmo.collection.*;
import com.itmo.server.url.SshConnection;
import com.itmo.server.url.UrlGetterDirectly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DatabaseManager {

    private static final String DB_URL = (System.getProperty("os.name").contains("dow")) ?
        new SshConnection().getUrl() :  new UrlGetterDirectly().getUrl();
    private static String USER;
    private static String PASS;
    private static final String COLLECTION_TABLE = "dragons";
    private static final String FILE_WITH_ACCOUNT = "account";
    private static final String USERS_TABLE = "users";
    private static final String pepper = "1@#$&^%$)3";


    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
             BufferedReader reader = new BufferedReader(fileReader)) {
            USER = reader.readLine();
            PASS = reader.readLine();
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
            passEncoder = new PassEncoder();
            System.out.println("db инициализована: " + dbUrl);
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

    public void insertDragon(Dragon d)  {
        try {
            String drtype = "NULL";
            if (d.getType() != null) drtype = "'" + d.getType() + "'";
            String state = "INSERT INTO "
                    + COLLECTION_TABLE + "(dragon_name, reg_date, age, wingspan, dragon_type, dragon_character, owner)\n" +
                    "VALUES ('" + d.getName() + "', '" + d.getCreatinoDateInFormat() + "', " + d.getAge() + ", "
                    + d.getWingspan() + ", " + drtype + ", '" + d.getCharacter() + "', '" + d.getOwnerName() + "')";
            PreparedStatement dragonItself = connection.prepareStatement(state);
            dragonItself.executeUpdate();
            PreparedStatement dragonCoords = connection.prepareStatement("INSERT INTO dragon_coordinates(dragon_id,x,y)" +
                    "VALUES (currval('generate_id'), " + d.getCoordinates().getX() + ", " + d.getCoordinates().getY() + ")");
            dragonCoords.executeUpdate();

            Person killer = d.getKiller();
            if (killer != null) {
                String hair = killer.getHairColor() == null ? "NULL" : "'" + killer.getHairColor() + "'";
                String nati = killer.getNationality() == null ? "NULL" : "'" + killer.getNationality() + "'";
                PreparedStatement dragonKiller =
                        connection.prepareStatement("INSERT INTO dragon_killers(dragon_id, killer_name, birthday, color,country)" +
                                "VALUES (currval('generate_id'), '" + killer.getName() + "', '" + killer.getBirthdayInFormat() + "', " + hair +
                                ", " + nati + ")");
                dragonKiller.executeUpdate();
                Location loc = killer.getLocation();
                PreparedStatement killerLoc =
                        connection.prepareStatement("INSERT INTO killers_locations(dragon_id, x,y,z,loc_name)\n" +
                                "VALUES (currval('generate_id'), " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() +
                                ", '" + loc.getName() + "')");
                killerLoc.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println("Ошибка при добавлении элемента в БД.");
        }
    }


    public Set<Dragon> getCollectionFromDatabase() throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM "+ COLLECTION_TABLE + " ds\n" +
                                "    inner join dragon_coordinates dc\n" +
                                "on ds.id = dc.dragon_id\n" +
                                "    left outer join dragon_killers dk\n" +
                                "    on dk.dragon_id = ds.id\n" +
                                "    left outer join killers_locations kl\n" +
                                "    on kl.dragon_id=ds.id");
        ResultSet resultSet = statement.executeQuery();
        HashSet<Dragon> hs = new HashSet<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("dragon_name");
            Date date = resultSet.getDate("reg_date");
            int age = resultSet.getInt("age");
            float wingspan = resultSet.getFloat("wingspan");
            String strType = resultSet.getString("dragon_type");
            DragonType type = strType == null ? null : Enum.valueOf(DragonType.class, strType);
            DragonCharacter character = Enum.valueOf(DragonCharacter.class, resultSet.
                    getString("dragon_character"));
            String ownerName = resultSet.getString("owner");
            Coordinates coordinates = new Coordinates(resultSet.getInt("x"), resultSet.getLong("y"));
            Person person = null;
            if(resultSet.getString("killer_name")!=null){
                 person = new Person(
                        resultSet.getString("killer_name"),
                        DateTimeAdapter.convertToLocalDateViaInstant(resultSet.getDate("birthday")),
                        Enum.valueOf(Color.class, resultSet.getString("color")),
                        Enum.valueOf(Country.class, resultSet.getString("country")),
                        new Location(
                                resultSet.getInt("x"),
                                resultSet.getLong("y"),
                                resultSet.getFloat("z"),
                                resultSet.getString("loc_name"))
                );

            }
            Dragon dragon = new Dragon(name, coordinates, date, age, wingspan, type, character, person);
            dragon.setOwnerName(ownerName);
            dragon.setId(id);
            hs.add(dragon);
        }
        return (Collections.synchronizedSet(hs));
    }

    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ?");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString("salt");
            String hash = passEncoder.getHash(user.getHashPass() + salt, "1@#$&^%$)3");
            statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ? and hashPass = ? and salt=?");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertUser(String login, String passHash){
        try {
            String salt = new SimplePasswordGenerator(true, true, true, true)
                    .generate(10,10);
            String hash = passEncoder.getHash(passHash + salt, pepper);
            PreparedStatement statement = connection.prepareStatement("insert into " +
                    USERS_TABLE + " (login, hashpass, salt)" + " VALUES ( '"+login+"', '" + hash +"', '" + salt +"' )");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertUser(User user){
        insertUser(user.getName(), user.getHashPass());
    }

    //ищем пользователя только по имени
    public boolean containsUserName(String login) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}