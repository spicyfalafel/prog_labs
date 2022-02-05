package com.itmo.managers;

import com.itmo.exceptions.InvalidArgument;
import com.itmo.exceptions.RecursiveScript;
import com.itmo.product.*;
import com.itmo.server.ExecuteCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.NavigableSet;
import java.util.Scanner;


import static com.itmo.product.Condition.*;
/**
 * Класс оболочка для считывания и обработки данных с файла
 */
public class FileManager extends IOManager {
    private Integer key;
    private String path;
    private File file;
    private String[] dataFile;
    private int lineNum = 0;
    private boolean script;
    private final StringManager stringManager;
    private ExecuteCommand executeCommand;

    public FileManager(HashTableManager products, String path, boolean script, NavigableSet<File> callHistory, StringManager stringManager, ExecuteCommand executeCommand) throws FileNotFoundException, RecursiveScript, NullPointerException {
        super(products);
        this.stringManager = stringManager;
        this.callHistory = callHistory;
        this.executeCommand = executeCommand;
        file = new File(path);
        if (callHistory.contains(file)) {
            throw new RecursiveScript("Error! Script call another script");
        }
        callHistory.add(new File(path));
        this.script = script;
        dataFile = split(read(), '\n');
        //dataFile = read().split("\n");
        for (int i = 0; i < dataFile.length; i++) {
            dataFile[i] = dataFile[i].replace("\r", "");
        }
    }

    private String read() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder data = new StringBuilder();
        while (scanner.hasNextLine()) {
            data.append(scanner.nextLine() + "\n");
        }
        return data.toString();
    }

    public void readData(User user) {
        if (script) {
            while (lineNum < dataFile.length - 1) {
                exit = executeCommand.doCommand(dataFile[lineNum++], user);
                if (exit) {
                    break;
                }
            }
        } else {
            int num = 1;
            for (int i = 0; i < dataFile.length - 1; i++) {
                String product = dataFile[i];
                Product pr = getProductFromCsv(product, num++);
                if (pr != null) {
                    products.insertToDb(key, pr);
                }

            }
        }
    }

    @Override
    public Person getOwner() {
        Person owner = null;
        try {
            personName = checkName(dataFile[lineNum++]);
            eyeColor = checkEyesColor(dataFile[lineNum++]);
            hairColor = checkHairColor(dataFile[lineNum++]);
            nationality = checkCountry(dataFile[lineNum++]);
            int skip = 0;
            try {
                x = Float.parseFloat(dataFile[lineNum++]);
                skip++;
                y = Long.parseLong(dataFile[lineNum++]);
                skip++;
                z = Double.parseDouble(dataFile[lineNum++]);
                skip++;
                if (dataFile[lineNum++].equals("")) {
                    locationName = null;
                } else {
                    locationName = dataFile[lineNum++];
                }
                location = new Location(x, y, z, locationName);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                lineNum -= skip;
                location = null;
            }
            owner = new Person(personName, ZonedDateTime.now(), eyeColor, hairColor, nationality, location);
        } catch (InvalidArgument | IndexOutOfBoundsException | NumberFormatException e) {
            stringManager.multiLine("Incorrect data in script on line: " + lineNum);
        }
        return owner;
    }

    @Override
    public User getUser() {
        return null;
    }

    private String[] split(String data, char ex) {
        int size = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == ex) {
                size++;
            }
        }
        if (size != 0) {
            size++;
        }
        String[] res = new String[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            StringBuilder arg = new StringBuilder();
            arg.append("");
            while (true) {
                if (j == data.length()) {
                    break;
                }
                if (data.charAt(j) == ex) {
                    j++;
                    break;
                } else {
                    arg.append(data.charAt(j++));
                }
            }
            res[i] = arg.toString();
        }
        return res;
    }

    @Override
    public Integer getId() {
        Integer id = null;
        try {
            id = Integer.parseInt(dataFile[lineNum++]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            stringManager.multiLine("Incorrect data in script on line: " + lineNum);
        }
        return id;
    }

    @Override
    public String getName() {
        String name = "";
        try {
            name = dataFile[lineNum++];
        } catch (ArrayIndexOutOfBoundsException e) {
            stringManager.multiLine("Incorrect data in script on line: " + lineNum);
        }
        return name;
    }


    @Override
    public Integer getKey() {
        Integer key = null;
        try {
            key = Integer.parseInt(dataFile[lineNum++]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            stringManager.multiLine("Incorrect data in script on line: " + lineNum);
        }
        return key;
    }

    @Override
    public Product getProduct() {
        try {
            productName = checkName(dataFile[lineNum++]);
            xCoord = Integer.parseInt(dataFile[lineNum++]);
            yCoord = checkY(dataFile[lineNum++]);
            price = checkPrice(dataFile[lineNum++]);
            manufactureCost = Long.parseLong(dataFile[lineNum++]);
            unitOfMeasure = checkUnitOfMeasure(dataFile[lineNum++]);
            owner = getOwner();
            if (owner != null) {
                return new Product(FIRST_ID++, productName, new Coordinates(xCoord, yCoord), LocalDateTime.now(), price, manufactureCost, unitOfMeasure, owner);
            } else {
                return null;
            }
        } catch (InvalidArgument | IndexOutOfBoundsException | NumberFormatException e) {
            stringManager.multiLine("Incorrect data in script on line: " + lineNum);
            return null;
        }
    }

    public Product getProductFromCsv(String data, int line) {
        String[] product = split(data, ',');
        int i = 0;
        try {
            key = Integer.parseInt(product[i++]);
            productName = checkName(product[i++]);
            xCoord = Integer.parseInt(product[i++]);
            yCoord = checkY(product[i++]);
            price = checkPrice(product[i++]);
            manufactureCost = Long.parseLong(product[i++]);
            unitOfMeasure = checkUnitOfMeasure(product[i++]);
            personName = checkName(product[i++]);
            eyeColor = checkEyesColor(product[i++]);
            hairColor = checkHairColor(product[i++]);
            nationality = checkCountry(product[i++]);
            try {
                x = Float.parseFloat(product[i++]);
                y = Long.parseLong(product[i++]);
                z = Double.parseDouble(product[i++]);
                if (product[i].equals("")) {
                    locationName = null;
                } else {
                    locationName = product[i];
                }
                location = new Location(x, y, z, locationName);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                location = null;
            }
            owner = new Person(personName, ZonedDateTime.now(), eyeColor, hairColor, nationality, location);
            return new Product(FIRST_ID++, productName, new Coordinates(xCoord, yCoord), LocalDateTime.now(), price, manufactureCost, unitOfMeasure, owner);
        } catch (IndexOutOfBoundsException | NumberFormatException | InvalidArgument e) {
            stringManager.multiLine("Incorrect data in file on line: " + line);
            return null;
        }
    }


}
