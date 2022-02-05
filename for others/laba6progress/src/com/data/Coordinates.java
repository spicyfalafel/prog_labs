package com.data;

public class Coordinates {
    private Integer x; //Максимальное значение поля: 770, Поле не может быть null
    private double y; //Значение поля должно быть больше -590

    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates(){}


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
