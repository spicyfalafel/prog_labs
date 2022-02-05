package com.itmo.collection.dragon.classes;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, LocalDateTime birthday, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.birthday = birthday;
    }


    public Person(String name, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }


    public String getBirthdayInFormat() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(birthday.toLocalDate());
    }

    public void setBirthdayInFormat(String yyyymmdd){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(yyyymmdd, formatter);
        birthday = dt.atStartOfDay();
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
