package com.itmo.collection;

import com.itmo.collection.dragon.classes.*;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
public class DragonForTable {
    private SimpleLongProperty id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private SimpleStringProperty name; //Поле не может быть null, Строка не может быть пустой

    private SimpleIntegerProperty x; //Поле не может быть null
    private SimpleLongProperty y;

    private SimpleStringProperty creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private SimpleIntegerProperty age; //Значение поля должно быть больше 0
    private SimpleFloatProperty wingspan; //Значение поля должно быть больше 0
    private SimpleStringProperty type; //Поле может быть null
    private SimpleStringProperty character; //Поле не может быть null

    private SimpleStringProperty killerName; //Поле не может быть null, Строка не может быть пустой
    private SimpleStringProperty birthdayInFormat; //Поле не может быть null
    private SimpleStringProperty hairColor; //Поле может быть null
    private SimpleStringProperty nationality; //Поле может быть null

    private SimpleIntegerProperty killerX;
    private SimpleLongProperty killerY; //Поле не может быть null
    private SimpleFloatProperty killerZ; //Поле не может быть null
    private SimpleStringProperty locationName; //Поле не может быть null

    private SimpleStringProperty creator;
    private Dragon dragon;

    public DragonForTable(long id){
        this.id = new SimpleLongProperty(id);
    }


    public DragonForTable(Dragon dragon) {
        this.dragon = dragon;
        this.id = new SimpleLongProperty(dragon.getId());
        this.name = new SimpleStringProperty(dragon.getName());
        this.x = new SimpleIntegerProperty(dragon.getCoordinates().getX());

        this.y = new SimpleLongProperty(dragon.getCoordinates().getY());
        this.creationDate = new SimpleStringProperty(dragon.getCreationDateInFormat());
        this.age = new SimpleIntegerProperty(dragon.getAge());
        this.wingspan = new SimpleFloatProperty(dragon.getWingspan());

        this.type = new SimpleStringProperty(dragon.getType() == null ? "null" : dragon.getType().name());
        this.character = new SimpleStringProperty(dragon.getCharacter().name());
        this.killerName = new SimpleStringProperty(dragon.getKiller()==null? "null" : dragon.getKiller().getName());
        this.birthdayInFormat = new SimpleStringProperty(dragon.getKiller()==null? "null" : dragon.getKiller().getBirthdayInFormat());

        this.hairColor = new SimpleStringProperty(dragon.getKiller()==null? "null" : dragon.getKiller().getHairColor().name());
        this.nationality = new SimpleStringProperty(dragon.getKiller()==null? "null" : dragon.getKiller().getNationality().name());
        this.killerX = new SimpleIntegerProperty(dragon.getKiller()==null ? 0 : dragon.getKiller().getLocation().getX());
        this.killerY = new SimpleLongProperty(dragon.getKiller()==null? 0: dragon.getKiller().getLocation().getY());
        this.killerZ = new SimpleFloatProperty(dragon.getKiller()==null? 0 : dragon.getKiller().getLocation().getZ());
        this.locationName = new SimpleStringProperty(dragon.getKiller()==null? "null" : dragon.getKiller().getLocation().getName());

        this.creator = new SimpleStringProperty(dragon.getOwnerName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DragonForTable that = (DragonForTable) o;
        return Objects.equals(id.getValue(), that.id.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getX() {
        return x.get();
    }

    public SimpleIntegerProperty xProperty() {
        return x;
    }

    public long getY() {
        return y.get();
    }

    public SimpleLongProperty yProperty() {
        return y;
    }

    public String getCreationDate() {
        return creationDate.get();
    }

    public SimpleStringProperty creationDateProperty() {
        return creationDate;
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public float getWingspan() {
        return wingspan.get();
    }

    public SimpleFloatProperty wingspanProperty() {
        return wingspan;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getCharacter() {
        return character.get();
    }

    public SimpleStringProperty characterProperty() {
        return character;
    }

    public String getKillerName() {
        return killerName.get();
    }

    public SimpleStringProperty killerNameProperty() {
        return killerName;
    }

    public String getBirthdayInFormat() {
        return birthdayInFormat.get();
    }

    public SimpleStringProperty birthdayInFormatProperty() {
        return birthdayInFormat;
    }

    public String getHairColor() {
        return hairColor.get();
    }

    public SimpleStringProperty hairColorProperty() {
        return hairColor;
    }

    public String getNationality() {
        return nationality.get();
    }

    public SimpleStringProperty nationalityProperty() {
        return nationality;
    }

    public int getKillerX() {
        return killerX.get();
    }

    public SimpleIntegerProperty killerXProperty() {
        return killerX;
    }

    public long getKillerY() {
        return killerY.get();
    }

    public SimpleLongProperty killerYProperty() {
        return killerY;
    }

    public float getKillerZ() {
        return killerZ.get();
    }

    public SimpleFloatProperty killerZProperty() {
        return killerZ;
    }

    public String getLocationName() {
        return locationName.get();
    }

    public SimpleStringProperty locationNameProperty() {
        return locationName;
    }

    public String getCreator() {
        return creator.get();
    }

    public SimpleStringProperty creatorProperty() {
        return creator;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public void setY(long y) {
        this.y.set(y);
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.set(creationDate);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setWingspan(float wingspan) {
        this.wingspan.set(wingspan);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setCharacter(String character) {
        this.character.set(character);
    }

    public void setKillerName(String killerName) {
        this.killerName.set(killerName);
    }

    public void setBirthdayInFormat(String birthdayInFormat) {
        this.birthdayInFormat.set(birthdayInFormat);
    }

    public void setHairColor(String hairColor) {
        this.hairColor.set(hairColor);
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public void setKillerX(int killerX) {
        this.killerX.set(killerX);
    }

    public void setKillerY(long killerY) {
        this.killerY.set(killerY);
    }

    public void setKillerZ(float killerZ) {
        this.killerZ.set(killerZ);
    }

    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }
}
