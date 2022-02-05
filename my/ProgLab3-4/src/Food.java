public class Food extends ThingToInteract {
    @Override
    public void interaction(AliveCreature user) {
        user.eat(this);
    }
    Food(String thingName, String description) {
        super(thingName, description);
    }
}
