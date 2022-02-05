package com.itmo.managers;

import com.itmo.exceptions.InvalidArgument;
import com.itmo.product.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Класс оболочка для считывания и обработки данных с консоли
 */
public class ConsoleManager extends IOManager{
    private final StringManager stringManager;
    private BufferedReader in;

    public ConsoleManager(HashTableManager products, StringManager stringManager, BufferedReader in) {
        super(products);
        this.stringManager = stringManager;
        this.in = in;
    }

    @Override
    public Integer getId() {
        int id;
        while (true){
            try{
                stringManager.multiLine("Enter id");
                id = checkId(in.readLine());
                break;
            } catch (NumberFormatException e) {
                stringManager.multiLine("Please enter int argument");
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
        return id;
    }

    @Override
    public String getName() {
        stringManager.multiLine("Enter file name");
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

//    @Override
//    public void readData() {
//        String command;
//        do {
//            stringManager.multiLine("Enter a command");
//            //printWriter.println("Enter a command");
//            command = scanner.nextLine().trim();
//        } while (!Execute.doCommand(command));
//    }

    @Override
    public Integer getKey() {
        int key;
        while (true){
            try{
                stringManager.multiLine("Enter key");
                key = checkKey(in.readLine());
                break;
            } catch (NumberFormatException | IOException e) {
                stringManager.multiLine("Please enter int argument");
            }
        }
        return key;
    }

    @Override
    public User getUser(){
        String name;
        String pass;
        while (true) {
            stringManager.multiLine("Enter username");
            try {
                name = checkName(in.readLine());
                stringManager.multiLine("Enter pass");
                pass = in.readLine();
                return new User(name, pass);
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
    }
    @Override
    public Product getProduct() {
        Product product = new Product(0, getProductName(), new Coordinates(getX(), getY()), LocalDateTime.now(), getPrice(), getManufactureCost(), getUnitOfMeasure(), getOwner());
        System.out.println(product);
        return product;
    }



    private String getProductName() {
        String arg;
        while (true) {
            stringManager.multiLine("Enter src.com.itmo.product name");
            try {
                arg = checkName(in.readLine());
                return arg;
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
    }

    private Integer getX() {
        Integer arg;
        while (true) {
           stringManager.multiLine("Enter X-coordinate");
            try {
                arg = Integer.parseInt(in.readLine());
                return arg;
            } catch (NumberFormatException | IOException e) {
                stringManager.multiLine("Please enter Integer argument");
            }
        }
    }
    private int getY() {
        int arg;
        while (true) {
            stringManager.multiLine("Enter Y-coordinate");
            try {
                arg = checkY(in.readLine());
                return arg;
            } catch (NumberFormatException e) {
                stringManager.multiLine("Please enter int argument");
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
    }

    private Long getPrice() {
        Long arg;
        while (true) {
            stringManager.multiLine("Enter price");
            try {
                arg = checkPrice(in.readLine());
                return arg;
            } catch (NumberFormatException e) {
                stringManager.multiLine("Please enter Long argument");
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
    }

    private Long getManufactureCost() {
        Long arg;
        while (true) {
            stringManager.multiLine("Enter manufacture cost");
            try {
                arg = Long.parseLong(in.readLine());
                return arg;
            } catch (NumberFormatException | IOException e) {
                stringManager.multiLine("Please enter Long argument");
            }
        }
    }

    private UnitOfMeasure getUnitOfMeasure() {
        UnitOfMeasure arg;
        while (true) {
            stringManager.multiLine("Enter unit of measure: KILOGRAMS, METERS, PCS, MILLILITERS, GRAMS");
            try {
                arg = checkUnitOfMeasure(in.readLine());
                return arg;
            }  catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }
    }

    public Person getOwner() {
        while (true) {
            stringManager.multiLine("Enter person name");
            try {
                personName = checkName(in.readLine());
                break;
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }

        while (true) {
            stringManager.multiLine("Enter eye color: GREEN, RED, BLUE, ORANGE");
            try {
                eyeColor = checkEyesColor(in.readLine());
                break;
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }

        while (true) {
            stringManager.multiLine("Enter hair color: BLACK, YELLOW, WHITE, ORANGE");
            try {
                hairColor = checkHairColor(in.readLine());
                break;
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }

        while (true) {
            stringManager.multiLine("Enter nationality: UNITED_KINGDOM, USA, FRANCE, VATICAN, THAILAND");
            try {
                nationality = checkCountry(in.readLine());
                break;
            } catch (InvalidArgument | IOException e) {
                stringManager.multiLine(e.getMessage());
            }
        }

        try {
            location = getLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("213");
        return new Person(personName, ZonedDateTime.now(), eyeColor, hairColor, nationality, location);

    }

    private Location getLocation() throws IOException {
        String check;
        stringManager.multiLine("Do yo want to enter location? Yes or no?");
        while (true) {
            check = in.readLine();

            //System.out.println("yesno");
            if (check.equals("yes") || check.equals("no"))
                break;
        }
        //String test = in.readLine();
        //System.out.println("227" + test);
        stringManager.multiLine("");

        if (check.equals("yes")) {
            while (true) {
                stringManager.multiLine("Enter x");
                System.out.println("232");
                try {
                    x = Float.parseFloat(in.readLine());
                    break;
                } catch (NumberFormatException e) {
                    stringManager.multiLine("Please enter float argument");
                }
            }

            while (true) {
                stringManager.multiLine("Enter y");
                //System.out.println("243");
                try {
                    y = Long.parseLong(in.readLine());
                    break;
                } catch (NumberFormatException e) {
                    stringManager.multiLine("Please enter long argument");
                }
            }

            while (true) {
                stringManager.multiLine("Enter z");
                //System.out.println("254");
                try {
                    z = Double.parseDouble(in.readLine());
                    break;
                } catch (NumberFormatException e) {
                    stringManager.multiLine("Please enter Double argument");
                }
            }

            stringManager.multiLine("Enter location name");
            locationName = in.readLine();
            if (locationName.isEmpty()) {
                locationName = null;
            }
            //System.out.println("268");
            return new Location(x, y, z, locationName);
        } else {
            //System.out.println("271");
            return null;
        }
    }
}
