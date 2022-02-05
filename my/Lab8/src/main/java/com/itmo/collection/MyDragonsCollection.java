package com.itmo.collection;

import com.itmo.collection.dragon.classes.*;
import com.itmo.exceptions.NotYourPropertyException;
import com.itmo.client.User;
import com.itmo.server.ServerMain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MyDragonsCollection implements Serializable {

    private Set<Dragon> dragons;
    private final Date creationDate;

    public MyDragonsCollection(){
        creationDate = new Date();
    }
    public MyDragonsCollection(Set<Dragon> dragons){
        this.dragons = Collections.synchronizedSet(dragons);
        creationDate = new Date();
    }

    public String show(){
        StringBuilder builder = new StringBuilder();
        if(dragons.size() == 0) return ServerMain.localeClass.getString("your_collection_is_empty.text");
        TreeSet<Dragon> treeSet = new TreeSet<>(dragons);
        treeSet.forEach(d ->{
                builder.append("----------\n").append(d.toString()).append("\n");
        });
        return builder.toString();
    }
    public String clear(User user){
        Set<Dragon> set = filterOwnDragon(user);
        set.forEach(d -> {
            try {
                remove(d, user);
            } catch (NotYourPropertyException e) {
                e.printStackTrace();
            }
        });

        return ServerMain.localeClass.getString("your_collection_is_cleared.text");
    }

    public Set<Dragon> filterOwnDragon(User user) {
        return Collections.synchronizedSet(dragons).stream()
                .filter(d -> d.getOwnerName().equals(user.getName())).collect(Collectors.toSet());
    }

    public String add(Dragon dragon){
        this.dragons.add(dragon);
        return ServerMain.localeClass.getString("dragon_has_been_added.text");
    }
    public String addIfMax(Dragon dragon){
        if(isMax(dragon)){
            return add(dragon);
        }
        return ServerMain.localeClass.getString("dragon_was_not_added.text")
                + " ("
                + ServerMain.localeClass.getString("its_not_the_strongest.text")
                + ")";
    }
    public boolean isMax(Dragon dragon){
        return (findMaxValue()<dragon.getValue());
    }
    public boolean isMin(Dragon dragon){
        return Collections.synchronizedSet(dragons).stream().
                noneMatch(d -> (d.getValue()<dragon.getValue()));
    }
    public String addIfMin(Dragon dragon){
        if(isMin(dragon)){
            return add(dragon);
        }else{
            return ServerMain.localeClass.getString("dragon_was_not_added.text")
                    + " ("
                    + ServerMain.localeClass.getString("its_not_the_weakest.text")
                    + ")";
        }
    }

    public static Dragon generateSimpleDragon(){
        Dragon d  = new Dragon("Guy", new Coordinates(10,10), 20, 200,
                DragonType.AIR, DragonCharacter.CUNNING,
                new Person("Killa", LocalDateTime.of(1999, Month.APRIL, 27,0,0),
                Color.BROWN, Country.RUSSIA, new Location(1,3L,5f, "Cave")));
        d.setCreationDate(Date.from(Instant.now()));
        d.setOwnerName("test");
        return d;
    }
    public static Dragon generateSimpleDragonWithType(DragonType type){
        Dragon d = generateSimpleDragon();
        d.setType(type);
        d.setCoordinates(new Coordinates((int) Math.round(Math.random()*600),
                 Math.round(Math.random()*360)));
        System.out.println("generated: ");
        System.out.println(d.getCoordinates().getX() + ", y: " + d.getCoordinates().getY());
        return d;
    }
    /**
     * удалить из коллекции все элементы, меньшие, чем заданный
     * @param dragon дракон с которым будут сравниваться все элементы коллекции
     */
    public String removeLower(Dragon dragon, User user) {
        StringBuilder builder = new StringBuilder();
        getLowerThan(dragon, user)
                .forEach(dr -> {
                    builder.append(ServerMain.localeClass.getString("deleted_dragon_with_id.text"))
                            .append(dr.getId()).append("\n");
                    try {
                        remove(dr, user);
                    } catch (NotYourPropertyException e) {
                        e.printStackTrace();
                    }
                });
        if(builder.length()==0) return ServerMain.localeClass.getString("no_dragons_less_than_that.text");
        return builder.toString();
    }
    public Set<Dragon> getLowerThan(Dragon dragon, User user){
        return filterOwnDragon(user).stream().filter(d -> d.getValue() < dragon.getValue()).collect(Collectors.toSet());
    }
    /**
     * фильтрует коллекцию, оставляет только тех, чьи имена начинаются с name
     * @param name является началом имени драконов которых нужно получить
     * @return сет драконов в отфильтрованном порядке
     */
    public Set<Dragon> filterStartsWithName(String name){
        return Collections.synchronizedSet(dragons).stream()
                .filter(d -> d.getName().trim().startsWith(name)).collect(Collectors.toSet());
    }

    /**
     * простой метод для вывода коллекции в обратном порядке
     */
    public String getElementsInDescendingOrder(){
        StringBuilder builder = new StringBuilder();
        Collections.synchronizedSet(dragons).stream().sorted((o1, o2) -> (int) (o2.getValue()-o1.getValue()))
                .forEach(d -> builder.append(d.getName())
                        .append(" with value ").append(d.getValue()).append("\n"));
        return builder.toString();
    }
    public boolean removeById(long id, User user) throws NotYourPropertyException{
        Dragon dragon = findById(id);
        if(dragon != null){
            remove(dragon, user);
            return true;
        }
        return false;
    }

    public void remove(Dragon d, User user) throws NotYourPropertyException {
        if(!user.getName().equals(d.getOwnerName())){
            throw new NotYourPropertyException(d.getOwnerName());
        }
        this.dragons.remove(d);
    }

    public float findMaxValue(){
        return (dragons.size()==0 ? 0 :
                Collections.synchronizedSet(dragons).stream()
                .max(Comparator.comparing(Dragon::getValue))
                        .get().getValue());
    }

    public Dragon findById(long id) {
        return dragons.stream().filter(d -> d.getId() == id).findAny().orElse(null);
    }

    public String printFieldAscendingWingspan(){
        StringBuilder builder = new StringBuilder();
        dragons.stream().map(Dragon::getValue)
                .sorted()
                .forEach(v -> builder.append(v).append(" "));
        return builder.toString();
    }

    public String getCollectionInfo(){
        return ServerMain.localeClass.getString("collection_type.text")
                +": com.itmo.Dragon\n " +
                ServerMain.localeClass.getString("initialization_date.text")+
                ": " + creationDate +
        "\n" + ServerMain.localeClass.getString("number_of_elements.text")+ ":" +  dragons.size();
    }

    public Set<Dragon> getDragons() {
        return dragons;
    }
}