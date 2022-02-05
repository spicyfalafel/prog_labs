import java.util.ArrayList;

public class Human extends AliveCreatureWIthMind implements WearClothes {
    Human(String name, Gender gender, Location location) {
        super(name, gender, location);
        clothes = new ArrayList<Cloth>();
    }
    private ArrayList<Cloth> clothes;
    //человек может носить сколько угодно одежды
    @Override
    public void wear(Cloth cloth) {
        clothes.add(cloth);
        System.out.println("На " + name + " надета вещь " + cloth.getThingName());
    }

    @Override
    public void takeOff(Cloth.ClothType clothType) {
        for (Cloth c :
                clothes) {
            if (c.isTypeOf(clothType)) {
                clothes.remove(c);
                System.out.println("С " + name + " снята вещь.");
            }
        }
    }
}