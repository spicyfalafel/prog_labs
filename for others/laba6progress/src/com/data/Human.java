package com.data;

import java.util.Date;
import java.util.Objects;

public class Human {
    static private Date convertString(String s) {
        // TODO: convert string to date
        return new Date();
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setBirthday(String birthday) {
        this.birthday = convertString(birthday);
    }

    private String name; //Поле не может быть null, Строка не может быть пустой
    private long age; //Значение поля должно быть больше 0
    private long height; //Значение поля должно быть больше 0
    private java.util.Date birthday;

    public Human(String name, long height, String birthday) {
        this.name = name;
        this.height = height;
        this.birthday = convertString(birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return height == human.height && name.equals(human.name) && birthday.equals(human.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, birthday);
    }

    public Human(){}

}

