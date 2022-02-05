package Collection;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Person implements Comparable<Person>{
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday; //Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null

    public Person(String name, Date birthday, Color eyeColor,
                  Color hairColor, Country nationality){
        this.name = name;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.birthday = birthday;
    }

    public Person(){}

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    @Override
    public String toString() {
        String birthday = new SimpleDateFormat("yyyy-MM-dd").format(this.birthday);
        return name + ", birthday: " + birthday +
                ", eyeColor: " + ((eyeColor == null) ? "null" : eyeColor) + ", haircolor: " +
                ((hairColor == null) ? "null" : hairColor) + ", nationality: " +
                ((nationality == null) ? " null" : " " + nationality);
    }

    @Override
    public int compareTo(Person o) {
        return this.birthday.compareTo(o.birthday);
    }
}
