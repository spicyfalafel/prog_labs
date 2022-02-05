package com.itmo.client;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private long id;

    private String name;
    private String hashPass;


    private double red, green, blue;

    public void setRandomColor(){
        red = Math.random();
        green = Math.random();
        blue =  Math.random();
    }
    public void setColor(double[] rgb){
        red = rgb[0];
        green = rgb[1];
        blue = rgb[2];
    }


    public User(String name, String hashPass){
        this.name = name;
        this.hashPass = hashPass;
    }

    public User(String name){
        this.name = name;
    }


    public Color getColor(){
        return Color.color(red, green, blue);
    }

}