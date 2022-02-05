public class DifferentLocsException extends Exception{
    private ThingToInteract thing;
    private AliveCreature user;
    public DifferentLocsException(ThingToInteract thing, AliveCreature user){
        this.thing = thing;
        this.user = user;
    }

    @Override
    public String getMessage() {
        return "DifferentLocsException!!!";
    }

    public ThingToInteract getThing() {
        return thing;
    }

    public AliveCreature getUser() {
        return user;
    }
}