package com.itmo.collection;

import com.itmo.collection.dragon.classes.Color;
import com.itmo.collection.dragon.classes.Country;
import com.itmo.collection.dragon.classes.DragonCharacter;
import com.itmo.collection.dragon.classes.DragonType;
import lombok.Data;

@Data
public class DragonWithStringFields {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String x, y;
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String age; //Значение поля должно быть больше 0
    private String wingspan; //Значение поля должно быть больше 0
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null

    private String killerName; //Поле не может быть null, Строка не может быть пустой
    private String birthday; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null

    private String killerX;
    private String killerY; //Поле не может быть null
    private String killlerZ; //Поле не может быть null
    private String locName; //Поле не может быть null


}
