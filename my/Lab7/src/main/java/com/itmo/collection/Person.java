package com.itmo.collection;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, LocalDateTime birthday, Color hairColor, Country nationality, Location location){
        this.name = name;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }


    public String getBirthdayInFormat(){
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(birthday.toLocalDate());
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String birthday = f.format(this.birthday);
        return name + ", birthday: " + birthday + ", haircolor: " +
                ((hairColor == null) ? "null" : hairColor) + ", nationality: " +
                ((nationality == null) ? " null" : " " + nationality) + ", location: " + location.toString();
    }
}
