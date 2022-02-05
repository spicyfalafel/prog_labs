public class Trash extends ThingToInteract {
    @Override
    public void interaction(AliveCreature user) {
        user.say("...");
    }

    @Override
    protected void interaction(AliveCreatureWIthMind user) {
        user.say("Это мусор! Мне не нужен мусор!");
    }

    Trash(String thingName, String description) {
        super(thingName, description);
    }
}