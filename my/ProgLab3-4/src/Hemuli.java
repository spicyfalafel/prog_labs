public class Hemuli extends AliveCreatureWIthMind implements WearClothes, CanInteract{
    //кто такие хемули описываю в main'e
    Hemuli(String name, Gender gender, Location currentLocation){
        super(name, gender, currentLocation);
    }

    @Override
    public void say(String msg) {
        super.say(msg + " Всё - суета.");
    }

    @Override
    public void saveThing(ThingToInteract thing) {
        super.saveThing(thing);
        say("Это явно заслуживает моего внимания!");
    }

    @Override
    public void enjoyNature(Nature nature) {
        say("Интересный экземпляр...");
    }

    @Override
    public void wear(Cloth cloth) {
        System.out.println("Хемуль не может надеть что-либо кроме своей мантии!!");
    }

    @Override
    public void takeOff(Cloth.ClothType clothType) {
        System.out.println("Хемуль не снимет свою мантию! Непорядочно!");
    }
}