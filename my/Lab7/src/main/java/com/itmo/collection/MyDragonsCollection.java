package com.itmo.collection;

import com.itmo.Exceptions.NotYourPropertyException;
import com.itmo.client.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyDragonsCollection implements Serializable {

    private Set<Dragon> dragons;
    private final Date creationDate;


    public MyDragonsCollection(Set<Dragon> dragons){
        this.dragons = Collections.synchronizedSet(dragons);
        creationDate = new Date();
    }

    public String show(){
        StringBuilder builder = new StringBuilder();
        if(dragons.size() == 0) return "Коллекция пуста. Добавьте дракончиков.";
        TreeSet<Dragon> treeSet = new TreeSet<>(dragons);
        treeSet.forEach(d ->{
                builder.append("----------\n").append(d.toString()).append("\n");
        });
        return builder.toString();
    }
    public String clear(User user){
        Set<Dragon> set = filterOwnDragon(user);
        for(Dragon d : set){
            try {
                remove(d, user);
            } catch (NotYourPropertyException ignore) {
            }
        }
        return "Принадлежащие вам драконы очищены";
    }

    private Set<Dragon> filterOwnDragon(User user) {
        return Collections.synchronizedSet(dragons).stream()
                .filter(d -> d.getOwnerName().equals(user.getName())).collect(Collectors.toSet());
    }

    public String add(Dragon dragon){
        Set<Long> setIds = Collections.synchronizedSet(dragons).stream().map(Dragon::getId).collect(Collectors.toSet());
        //генерация id
        for(long i = 0; i<Long.MAX_VALUE;i++){
            if(!setIds.contains(i)){
                dragon.setId(i);
                this.dragons.add(dragon);
                return "Дракон добавлен";
            }
        }
        return "Дракон не добавлен потому что не удалось сгенерировать для него id.";
    }
    public String addIfMax(Dragon dragon){
        if(isMax(dragon)){
            return add(dragon);
        }
        return "Не добавлен, т.к. не больший";
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
            return "Не добавлен, т.к. не меньший";
        }else{
            return add(dragon);
        }
    }

    public Dragon generateSimpleDragon(){
        return new Dragon("Guy", new Coordinates(1,2), 20, 200,
                DragonType.AIR, DragonCharacter.CUNNING,
                new Person("Killa", LocalDateTime.of(1999, Month.APRIL, 27,0,0),
                        Color.BROWN, Country.RUSSIA, new Location(1,3L,5f, "Cave")));
    }
    /**
     * удалить из коллекции все элементы, меньшие, чем заданный
     * @param dragon дракон с которым будут сравниваться все элементы коллекции
     */
    public String removeLower(Dragon dragon, User user) {
        StringBuilder builder = new StringBuilder();
        filterOwnDragon(user).stream().filter(d -> d.getValue() < dragon.getValue())
                .forEach(dr -> {
                    builder.append("Удален дракон с id ").append(dr.getId()).append("\n");
                    try {
                        remove(dr, user);
                    } catch (NotYourPropertyException e) {
                        e.printStackTrace();
                    }
                });
        if(builder.length()==0) return "Нет драконов меньше чем заданный";
        return builder.toString();
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
    public String printDescending(){
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
        return "Тип коллекции: com.itmo.Dragon\nДата инициализации: " + creationDate +
        "\nКоличество элементов: " + dragons.size();
    }

    public Set<Dragon> getDragons() {
        return dragons;
    }
}