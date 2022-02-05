package com.itmo.collection.dragon.classes;

import lombok.Getter;

import java.io.Serializable;

public class Coordinates implements Serializable {
    @Getter
    private Integer x; //Поле не может быть null
    @Getter
    private long y;

    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;
    }

}
