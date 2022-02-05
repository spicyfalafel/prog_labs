import java.util.HashMap;
import java.util.Objects;
public class
Moomin extends AliveCreatureWIthMind implements Alive, WearClothes {
    // имя Муми-троля хранится в name предка AliveCreature,
    // особых проблем в этом пока не вижу...

    private Backpack backpack;

    // HashMap для пар Тип одежды - Одежда,
    // на МумиТроле одновременно может быть только 3 предмета
    // одежды, по 1 на каждый тип
    HashMap<Cloth.ClothType, Cloth> clothes;

    Moomin(String name, Gender gender, Location currLocation){
        super(name, gender, currLocation);
        backpack = new Backpack(3, getName());
        clothes = new HashMap<Cloth.ClothType, Cloth>();
    }

    @Override
    public void enjoyNature(Nature nature) {
        sleep();
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void saveThing(ThingToInteract thing) {
        backpack.addThingToBackpack(thing);
    }
    // может пригодиться
    public void removeSavedThingNumber(int num){
        backpack.removeThingFromBackback(num);
    }
    @Override
    public void removeSavedThing() {
        backpack.removeThingFromBackback(0);
    }
    @Override
    public String getName() {
        return name;
    }

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        return name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moomin moomin = (Moomin) o;
        return Objects.equals(currentLocation, moomin.currentLocation) &&
                Objects.equals(name, moomin.name) &&
                gender == moomin.gender &&
                currentState == moomin.currentState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentLocation, name, gender, currentState);
    }

    @Override
    public void wear(Cloth cloth) {
        clothes.remove(cloth.getClothType());
        clothes.put(cloth.getClothType(), cloth);
        System.out.println("Теперь на " + getName() + " надето " + cloth.getThingName());
    }

    @Override
    public void takeOff(Cloth.ClothType clothType) {
        if (clothes.containsKey(clothType)){
            clothes.remove(clothType);
            System.out.println(name + " сня" + (gender.equals(Gender.FEMALE) ? "ла " : "л ")
                    + clothType.rus);
        }
    }

    public void setClothes(HashMap<Cloth.ClothType, Cloth> clothes){
        this.clothes = clothes;
    }
}