package com.itmo.product;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product implements Comparable<Product> {
    private User userOwner;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле может быть null, Значение поля должно быть больше 0
    private Long manufactureCost; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле не может быть null

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getPrice() {
        return price;
    }

    public Long getManufactureCost() {
        return manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public User getUserOwner() {
        return userOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(price, product.price) && Objects.equals(manufactureCost, product.manufactureCost) && unitOfMeasure == product.unitOfMeasure && Objects.equals(owner, product.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, price, manufactureCost, unitOfMeasure, owner);
    }


    @Override
    public String toString() {
        return "Product{" +
                "OWNER:" + (userOwner == null ? "none" : userOwner.getName()) +
                ",  id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }

    public String toCsv() {
        if (price != null) {
            return id + "," + name + "," + coordinates.toCsv() + "," + price + "," + manufactureCost + "," + unitOfMeasure + "," + owner.toCsv();
        }
        return id + "," + name + "," + coordinates.toCsv() + "," + "," + manufactureCost + "," + unitOfMeasure + "," + owner.toCsv();

    }

    public Product(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Long price, Long manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    public Product(Integer id, User userOwner, String name, Coordinates coordinates, LocalDateTime creationDate, Long price, Long manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.userOwner = userOwner;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    public Product(User userOwner, String name, Coordinates coordinates, LocalDateTime creationDate, Long price, Long manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {

        this.userOwner = userOwner;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Product o) {
        if (this.manufactureCost < o.getManufactureCost()) {
            return -1;
        }

        if (this.manufactureCost > o.getManufactureCost()) {
            return 1;
        }

        return 0;
    }
}
