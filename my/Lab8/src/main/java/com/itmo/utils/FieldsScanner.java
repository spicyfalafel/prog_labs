package com.itmo.utils;

import com.itmo.collection.dragon.classes.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Scanner;

/**
 * класс для ввода полей элемента.
 */
public class FieldsScanner {
    private static Scanner sc;
    private static FieldsScanner fs;

    private FieldsScanner(Scanner scanner) {
        sc = scanner;
    }

    public void configureScanner(Scanner scanner) {
        sc = scanner;
    }

    public static FieldsScanner getInstance() {
        if (fs == null) {
            fs = new FieldsScanner(new Scanner(System.in));
        }
        return fs;
    }

    /**
     * @param nameOfWhatYouWantToScan че вводить?
     * @return введённая пользователем строка (может быть пустой)
     */
    public String scanLine(String nameOfWhatYouWantToScan) {
        System.out.println("Введите " + nameOfWhatYouWantToScan);
        if (sc.hasNextLine()) return sc.nextLine().trim();
        else return "";
    }

    public String scanLine() {
        return sc.nextLine().trim();
    }

    /**
     * ПРИВАТНЫЙ МЕТОД ДЛЯ НЕПУСТОЙ СТРОКИ
     *
     * @param cheVvodit че вводить?
     * @return введенная пользователем строка (не пустая)
     */
    private String scanNotEmptyLine(String cheVvodit) {
        String res = scanLine(cheVvodit);
        while (res.trim().isEmpty()) {
            System.out.println("Строка не должна быть пустой или состоять только из пробелов.");
            System.out.println("Введите " + cheVvodit + " заново.");
            res = sc.nextLine();
        }
        return res.trim();
    }


    public String scanStringNotEmpty(String cheVvodit) {
        String str = scanLine(cheVvodit);
        while (str == null || str.equals("")) {
            System.out.println("Не может быть пустой. Введите " + cheVvodit);
            str = sc.nextLine();
        }
        return str.trim();
    }


    public boolean scanYN() {
        String ans = FieldsScanner.getInstance().scanStringNotEmpty("y/n");
        while (true) {
            switch (ans) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Введите y/n");
            }
        }
    }


    /**
     * метод для сканирования любого Enum. проверяет, является ли введенная
     * пользователем строка элементом enum'а, который передается во втором аргументе.
     *
     * @param canBeNull может ли быть Enum пустым?
     * @param enumType  тип перечисления
     * @return enum
     */
    public Enum<?> scanEnum(boolean canBeNull, Class<? extends Enum> enumType) {
        while (true) {
            String str = scanLine();
            try {
                if (str.equals("") && canBeNull) return null;
                else if (str.equals("")) {
                    throw new NullPointerException();
                }
                return Enum.valueOf(enumType, str);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Пожалуйста, введите одно из значений enum'а.");
            }
        }
    }

    /**
     * метод для сканирования аргумента который должен быть integer
     *
     * @param cheVvodit    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число int
     */
    public int scanInteger(String cheVvodit, boolean positiveOnly) {
        while (true) {
            String input = scanNotEmptyLine(cheVvodit);
            int res;
            try {
                res = Integer.parseInt(input);
                if (positiveOnly && (res <= 0)) {
                    System.out.println("необходимо ввести число большее нуля");
                } else {
                    return res;
                }
            } catch (Exception e) {
                System.out.println("введите целое число");
            }
        }
    }

    /**
     * метод для сканирования аргумента который должен быть float
     *
     * @param cheVvodit    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число float
     */
    public float scanFloat(String cheVvodit, boolean positiveOnly) {
        while (true) {
            String input = scanNotEmptyLine(cheVvodit);
            float res;
            try {
                res = Float.parseFloat(input);
                if (positiveOnly && (res <= 0)) {
                    System.out.println("необходимо ввести число большее нуля");
                } else {
                    return res;
                }
            } catch (Exception e) {
                System.out.println("введите число");
            }
        }
    }

    /**
     * метод для сканирования аргумента который должен быть long
     *
     * @param cheVvodit    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число long
     */
    public long scanLong(String cheVvodit, boolean positiveOnly) {
        while (true) {
            String input = scanNotEmptyLine(cheVvodit);
            long res;
            try {
                res = Long.parseLong(input);
                if (positiveOnly && (res <= 0)) {
                    System.out.println("необходимо ввести число большее нуля");
                } else {
                    return res;
                }
            } catch (Exception e) {
                System.out.println("введите число");
            }
        }
    }


    /**
     * Scan local date time no null local date time.
     *
     * @param cheVvodit the che vvodit
     * @return the local date time
     */
    public LocalDateTime scanLocalDateTimeNoNull(String cheVvodit) {
        System.out.println(cheVvodit);
        int god = scanInteger("год", true);
        System.out.println("Введите месяц\nДоступные значения Month: ");
        Month mon;
        Arrays.stream(Month.values()).forEach(m -> {
            System.out.print(m + " ");
        });
        System.out.println();
        while (true) {
            try {
                mon = (Month) scanEnum(false, Month.class);
                int day = scanInteger("число", true);
                return LocalDateTime.of(god, mon, day, 0, 0);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("введите название месяца правильно");
            } catch (DateTimeException e) {
                System.out.println("Число не то для месяца. еще раз");
            }
        }
    }

    public Location scanLocation(String cheVvodit) {
        System.out.println("введите " + cheVvodit);
        int x = scanInteger("координата Х места", false);
        long y = scanLong("координата Y места", false);
        float z = scanFloat("координата Z места", false);
        String locName = scanStringNotEmpty("название места");
        return new Location(x, y, z, locName);
    }

    /**
     * сканирует всего дракона. проверяет правильность ввода полей. учитывает, какие поля
     * могут быть null, а какие поля-числа больше нуля
     *
     * @return дракон dragon
     */
    public Dragon scanDragon() {
        String name = scanStringNotEmpty("имя дракона");
        System.out.println("Координаты.");
        int x = scanInteger("Х", false);
        int y = scanInteger("Y", false);
        Coordinates coordinates = new Coordinates(x, y);
        int age = scanInteger("возраст дракона", true);
        float wingspan = scanFloat("размах крыльев (это число)", true);
        System.out.println("Введите тип дракона. Доступные типы: ");
        for (DragonType t : DragonType.values()) {
            System.out.print(t + " ");
        }
        System.out.println();
        DragonType type = (DragonType) scanEnum(true, DragonType.class);
        System.out.println("Введите характер дракона. Доступные типы: ");
        for (DragonCharacter t : DragonCharacter.values()) {
            System.out.print(t + " ");
        }
        System.out.println();
        DragonCharacter character = (DragonCharacter) scanEnum(false, DragonCharacter.class);
        Person killer = scanPerson("убийца дракона");
        return new Dragon(name, coordinates, age,
                wingspan, type, character, killer);
    }

    /**
     * сканирует всего Person. проверяет правильность ввода полей. учитывает, какие поля
     * могут быть null, а какие поля-числа больше нуля
     *
     * @param cheVvodit че вводить?
     * @return человiк person
     */
    public Person scanPerson(String cheVvodit) {
        System.out.println("введите " + cheVvodit);
        String name = scanLine("имя");
        if (name.equals("")) return null; // Person может быть пустым
        LocalDateTime birthday = scanLocalDateTimeNoNull("дата рождения");
        System.out.println("Введите цвет волос. Доступные типы: ");
        for (Color t : Color.values()) {
            System.out.print(t + " ");
        }
        System.out.println();
        Color hairColor = (Color) scanEnum(true, Color.class);
        System.out.println("Введите национальность. Доступные типы: ");
        for (Country t : Country.values()) {
            System.out.print(t + " ");
        }
        System.out.println();
        Country country = (Country) scanEnum(true, Country.class);
        Location loc = scanLocation("локацию");
        return new Person(name, birthday, hairColor, country, loc);
    }
}