package com.itmo.product;

import com.itmo.product.eyes.Color;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private com.itmo.product.eyes.Color eyeColor; //Поле не может быть null
    private com.itmo.product.hair.Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле может быть null

    public String getName() {
        return name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public com.itmo.product.hair.Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && eyeColor == person.eyeColor && hairColor == person.hairColor && nationality == person.nationality && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eyeColor, hairColor, nationality, location);
    }

    public Person(String name, ZonedDateTime birthday, Color eyeColor, com.itmo.product.hair.Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public String toCsv() {
        if (location != null && hairColor != null) {
            return name + "," + eyeColor + "," + hairColor + "," + nationality + "," + location.toCsv();
        }
        if (location != null) {
            return name + "," + eyeColor + ","  + "," + nationality + "," + location.toCsv();
        }
        if (hairColor != null) {
            return name + "," + eyeColor + "," + hairColor + "," + nationality + ",";
        }
        return name + "," + eyeColor + "," + "," + nationality + ",";


    }
}
