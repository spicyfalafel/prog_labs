public class Value extends ThingToInteract {
    @Override
    public void interaction(AliveCreature user) {
        try{
            user.saveThing(this);
        }catch (MyNullPointerException e){
            System.out.println("что-то пошло не так...");
        }
    }
    Value(String thingName, String description) {
        super(thingName, description);
    }
}
