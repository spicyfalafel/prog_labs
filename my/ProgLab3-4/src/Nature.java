public class Nature extends ThingToInteract {
    Nature(String thingName, String description) {
        super(thingName, description);
    }

    @Override
    public void interaction(AliveCreature user){
        user.say("???");
    }
    @Override
    public void interaction(AliveCreatureWIthMind user){
        user.enjoyNature(this);
    }

}