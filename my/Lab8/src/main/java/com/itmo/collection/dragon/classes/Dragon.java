package com.itmo.collection.dragon.classes;

import com.itmo.client.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Dragon implements Comparable<Dragon>, Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private float wingspan; //Значение поля должно быть больше 0
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null
    private Person killer; //Поле может быть null

    @Getter @Setter
    private String ownerName;

    @Setter @Getter
    private User user;


    /**
     * Конструктор со всеми не автогенерируемыми полями
     *
     * @param name        - имя дракона
     * @param coordinates - координаты
     * @param age         - возраст дракона. value = age * wingspan
     * @param wingspan    - размах крыла
     * @param type        - тип дракона
     * @param character   - характер дракона
     * @param killer      - убийца дракона
     */
    public Dragon(String name, Coordinates coordinates, int age, float wingspan,
                  DragonType type, DragonCharacter character, Person killer){
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.wingspan = wingspan;
        this.type = type;
        this.character = character;
        this.killer = killer;
        creationDate = new Date();
    }

    public Dragon(long id){
        this.id = id;
    }
    public Dragon(float value){
        this.age = 1;
        this.wingspan = value;
    }


    public Dragon(String name, Coordinates coordinates, Date creationDate, int age, float wingspan,
                  DragonType type, DragonCharacter character, Person killer){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.wingspan = wingspan;
        this.type = type;
        this.character = character;
        this.killer = killer;
    }

    public Dragon(Long id, String name, Coordinates coordinates, int age, float wingspan, DragonType type,
                  DragonCharacter character, Person killer){
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.wingspan = wingspan;
        this.type = type;
        this.character = character;
        this.killer = killer;
        creationDate = new Date();
        this.id = id;
    }

    public float getValue(){
        return this.wingspan*this.age;
    }

    public Dragon(String name, Coordinates coordinates, int age, float wingspan,
                  DragonCharacter character){
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.wingspan = wingspan;
        this.character = character;
        creationDate = new Date();
    }

    public String getCreationDateInFormat(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }



    @Override
    public String toString() {
        return "owner: " + ownerName + "\n" +
                "id: " + this.id + "\n" +
                "name: " + this.name + "\n" +
                "coordinates: " + coordinates.getX().toString() +
                ", " + coordinates.getY() + "\n" +
                "creationDate: " + this.creationDate.toString() + "\n" +
                "age: " + this.age + "\n" +
                "wingspan: " + this.wingspan + "\n" +
                "type: " + ((type == null) ? "null" : this.type.toString()) + "\n" +
                "character: " + this.character.toString() + "\n" +
                "killer: " + ((this.killer == null) ? "null" : this.killer.toString());
    }

    /**
     * драконы сравниваются по value = age * wingspan
     * @param dragon сравниваемый дракон
     */
    @Override
    public int compareTo(Dragon dragon) {
        return Float.compare(getValue(), dragon.getValue());
    }
}