import java.util.ArrayList;

public class Backpack {
    //класс рюкзака, ничего интересного тут нет
    final private int BACKPACK_MAX_NUM_OF_THINGS;
    private ArrayList<ThingToInteract> backpack;
    private String owner;

    public Backpack(int backpack_max_num_of_things, String owner) {
        BACKPACK_MAX_NUM_OF_THINGS = backpack_max_num_of_things;
        this.owner = owner;
        backpack = new ArrayList<ThingToInteract>();
    }
    //может понадобиться
    public Backpack(int backpack_max_num_of_things, ArrayList<ThingToInteract> things, String owner) {
        BACKPACK_MAX_NUM_OF_THINGS = backpack_max_num_of_things;
        backpack.addAll(things);
    }

    public void removeThingFromBackback(int num){
        if(num+1<backpack.size()) backpack.remove(num);
    }

    public void addThingToBackpack(ThingToInteract thing){
        if(backpack.size()< BACKPACK_MAX_NUM_OF_THINGS) {
            System.out.println("В рюкзак у владельца " + owner + " добавлен предмет "
                    + thing.getThingName());
            System.out.println("В рюкзаке теперь " + (backpack.size()+1) + " предмет(ов).");
            backpack.add(thing);
        }
    }

    public void removeThingFromBackpack(ThingToInteract thing){
        backpack.remove(thing);
        System.out.println("Из рюкзака владельца " + owner + " убрано" + thing.getThingName());
    }
}