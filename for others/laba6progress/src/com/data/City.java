package com.data;


import java.util.Date;

public class City implements Comparable<City> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private long metersAboveSeaLevel;
    private java.util.Date establishmentDate;
    private Double agglomeration;
    private Climate climate; //Поле может быть null
    private Human governor; //Поле может быть null

    public City(Long id, String name, Coordinates coordinates, Date creationDate, long area, Integer population, long metersAboveSeaLevel, Date establishmentDate, Double agglomeration, Climate climate, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.governor = governor;
    }
    public City(){}

    @Override
    public String toString() {
        return this.name + " " + this.coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(City o) {
        if (this.name.compareTo(o.name) != 0) {
            return this.name.compareTo(o.name);
        }
        return this.population.compareTo(o.population);
    }

    public Long getId() {
        return id;
    }

//    public Human getByGovernor(){
//        return governor;
//    }

    public Double getAg() {
        return agglomeration;
    }

}
