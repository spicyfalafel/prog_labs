package Collection;

import java.time.LocalDateTime;
import java.util.Date;

public class Product implements Comparable<Product>{
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле не может быть null

    private static Integer idInc = 0;

    public Product(){}

    public Product(String name,
                   Coordinates coordinates,
                   Double price,
                   UnitOfMeasure unitOfMeasure,
                   Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        creationDate = LocalDateTime.now();
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        generateId();
    }

    private void generateId(){
        this.id = idInc++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public static Integer getIdInc() {
        return idInc;
    }

    public void changeId(Integer id){
        this.id = id;
    }

    public static void setIdInc(Integer idInc) {
        Product.idInc = idInc;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + ((price == null) ? "null" : price)+
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return (int) (this.getPrice() - o.getPrice());
    }
}
