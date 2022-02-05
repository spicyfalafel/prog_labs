package com.itmo.product;

import java.util.Objects;

public class Location {
    private float x;
    private long y;
    private Double z; //Поле не может быть null
    private String name; //Поле может быть null

    public float getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public Location(float x, long y, Double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Float.compare(location.x, x) == 0 && y == location.y && Objects.equals(z, location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }

    public String toCsv() {
        if (name != null) {
            return x + "," + y + "," + z + "," + name;
        }
        return x + "," + y + "," + z + ",";
    }
}
