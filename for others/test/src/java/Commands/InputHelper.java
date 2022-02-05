package Commands;

import Collection.*;

import java.time.DateTimeException;
import java.time.Month;
import java.util.Date;
import java.util.Scanner;

/**
 * класс для ввода полей элемента.
 */
public class InputHelper {
    private Scanner sc;

    public InputHelper(Scanner sc){
        this.sc = sc;
    }

    /**
     *
     * @param inp что вводим?
     * @return введённая пользователем строка (может быть пустой)
     */
    private String scanLine(String inp){
        System.out.println("введите " + inp);
        return sc.nextLine().trim();
    }
    private String scanLine(){
        return sc.nextLine().trim();
    }

    /**
     * для непустой строки
     * @param inp что вводим?
     * @return введенная пользователем строка (не пустая)
     */
    private String scanNotEmptyLine(String inp){
        String res = scanLine(inp);
        while(res.trim().isEmpty()) {
            System.out.println("Строка не должна быть пустой или состоять только из пробелов.");
            System.out.println("Введите " + inp + " заново.");
            res = sc.nextLine();
        }
        return res.trim();
    }

    /**
     * метод для ввода аргументов-строк.
     *
     * @param inp что вводить?
     * @return введенное пользователем, скорее всего, имя
     */
    public String scanStringArg(String inp){
        String str = scanLine(inp);
        while(str==null || str.equals("")){
            System.out.println("Не может быть пустой. Введите " + inp + " еще раз.");
            str = sc.nextLine();
        }
        return str;
    }

    /**
     * метод для сканирования любого Enum. проверяет, является ли введенная
     * пользователем строка элементом enum'а, который передается во втором аргументе.
     *
     * @param canBeNull может ли быть Enum пустым?
     * @param enumType  тип перечисления
     * @return enum
     */
    public Enum<?> scanEnum(boolean canBeNull, Class<? extends Enum> enumType){

        while(true) {
            String str = scanLine();
            try {
                return parseEnum(str, canBeNull, enumType);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Пожалуйста, введите одно из значений enum'а.");
            }
        }
    }
    public Enum<?> parseEnum(String str, boolean canBeNull, Class<? extends Enum> enumType){
        if (str.equals("") && canBeNull) return null;
        else if (str.equals("")){
            throw new NullPointerException();
        }
        return Enum.valueOf(enumType, str);
    }

    /**
     * метод для сканирования аргумента который должен быть integer
     *
     * @param inp    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число int
     */
    public int scanInteger(String inp, boolean positiveOnly){
        while(true) {
            String input = scanNotEmptyLine(inp);
            Integer parsed = parseInteger(input, positiveOnly);
            if(parsed !=null) return parsed;
        }
    }

    private Integer parseInteger(String input, boolean positiveOnly) {
        int res;
        try{
            res = Integer.parseInt(input);
            if(positiveOnly && (res<=0)){
                System.out.println("необходимо ввести число большее нуля");
            }else{
                return res;
            }
        }catch (Exception e){
            System.out.println("введите целое число");
        }
        return null;
    }

    /**
     * метод для сканирования аргумента который должен быть float
     *
     * @param inp    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число float
     */
    public float scanFloat(String inp, boolean positiveOnly){
        while(true) {
            String input = scanNotEmptyLine(inp);
            float res;
            try{
                res = Float.parseFloat(input);
                if(positiveOnly && (res<=0)){
                    System.out.println("необходимо ввести число большее нуля");
                }else{
                    return res;
                }
            }catch (Exception e){
                System.out.println("введите число");
            }
        }
    }
    public Double scanDouble(String inp, boolean positiveOnly, boolean canBeNull){
        while(true) {
            String input;
            if(canBeNull) input = scanLine(inp);
            else input = scanNotEmptyLine(inp);
            Double res;
            try{
                res = Double.parseDouble(input);
                if(positiveOnly && (res<=0)){
                    System.out.println("необходимо ввести число большее нуля");
                }else{
                    return res;
                }
            }catch (Exception e){
                System.out.println("введите число");
            }
        }
    }

    public Date scanDateNoNull(String inp){
        System.out.println(inp);
        int god = scanInteger("год", true);
        System.out.println("Доступные значения Month: ");
        Month mon;
        for(Month t : Month.values()){
            System.out.println(t + " ");
        }
        while(true){
            String str = scanNotEmptyLine(inp);
            try{
                System.out.println("введите месяц:");
                mon = (Month) scanEnum(false, Month.class);
                int day = scanInteger("день", true);
                if(day <=31 && day >=1){
                    return new Date(god, mon.getValue(), day);
                }else{
                    System.out.println("Неправильное число месяца");
                }
            }catch(IllegalArgumentException|NullPointerException e){
                System.out.println("введите название месяца правильно");
            }
        }
    }


    /**
     * сканирует весь Product. проверяет правильность ввода полей. учитывает, какие поля
     * могут быть null, а какие поля-числа больше нуля
     *
     * @return product
     */
    public Product scanProduct(){
        String name = scanStringArg("название продукта");
        System.out.println("Координаты.");
        Double x = scanDouble("Х", false, false);
        float y = scanFloat("Y", false);
        Coordinates coordinates = new Coordinates(x, y);
        Double price = scanDouble("цена продукта", true, true);
        System.out.println("Введите UnitOfMeasure. Доступные типы: ");
        for(UnitOfMeasure t : UnitOfMeasure.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        UnitOfMeasure measure = (UnitOfMeasure ) scanEnum( false, UnitOfMeasure.class);

        Person owner = scanPerson("покупателя");
        return new Product(name, coordinates, price, measure, owner);
    }

    /**
     * сканирует всего Person. проверяет правильность ввода полей. учитывает, какие поля
     * могут быть null, а какие поля-числа больше нуля
     *
     * @param inp что вводим
     * @return person
     */
    public Person scanPerson(String inp){
        System.out.println("введите " + inp);
        String name = scanNotEmptyLine("имя");
        Date birthday = scanDateNoNull("дату рождения");
        System.out.println("Введите цвет волос. Доступные типы: ");
        for(Color t: Color.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        Color hairColor = (Color) scanEnum( false, Color.class);

        System.out.println("Введите цвет глаз. Доступные типы: ");
        for(Color t: Color.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        Color eyeColor = (Color) scanEnum( true, Color.class);

        System.out.println("Введите национальность. Доступные типы: ");
        for(Country t : Country.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        Country country = (Country) scanEnum( true, Country.class);
        return new Person(name, birthday, eyeColor, hairColor, country);
    }
}