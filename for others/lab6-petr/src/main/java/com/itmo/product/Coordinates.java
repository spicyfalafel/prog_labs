package com.itmo.product;

import java.util.Objects;

public class Coordinates {
    private Integer x; //Поле не может быть null
    private int y; //Максимальное значение поля: 693

    public Coordinates(Integer x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return y == that.y && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toCsv() {
        return x + "," + y;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
