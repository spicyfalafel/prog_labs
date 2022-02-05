import java.util.Objects;
// в моей программе с вещами могут взаимодействовать только живые существа!
abstract public class   ThingToInteract{
    abstract public void interaction(AliveCreature user);
    // таким образом я могу переписать interaction(AliveCreatureWithMindAndFeelings) в
    // каком-то классе предмета, но не обязан. если не перепишу, то будет вызываться
    // interaction(AliveCreature user)
    // живые существа либо с разумом, либо нет
    // тогда эти два метода - ок
    protected void interaction(AliveCreatureWIthMind user){
        interaction((AliveCreature) user);
    }

    // главный метод для взаимодействия персонажа и предмета
    // вызывается еще из AliveCreature.interact(ThingToInteract thing)
    void mainInteraction(AliveCreature user){
        setCurrentUser(user);
        if(InteractManager.interactionPreparing(user, this)){
            if(user instanceof AliveCreatureWIthMind){
                interaction((AliveCreatureWIthMind) user);
            }else{
                interaction(user);
            }
        }
    }

    // в классе есть currentUser, т.к. при использовании предметов
    // из класса вещи вызывается interaction(), который вызывает
    // метод действия user'a
    protected AliveCreature currentUser;
    private void setCurrentUser(AliveCreature currentUser) {
        this.currentUser = currentUser;
    }
    public AliveCreature getCurrentUser(){
        return currentUser;
    }


    protected String thingName;
    protected String description;
    protected Location location;


    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation(){
        return location;
    }

    ThingToInteract(String thingName, String description){
        this.thingName = thingName;
        this.description = description;
    }

    @Override
    public String toString() {
        return thingName;
    }

    String getThingName() {
        return thingName;
    }

    String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThingToInteract that = (ThingToInteract) o;
        return Objects.equals(currentUser, that.currentUser) &&
                Objects.equals(thingName, that.thingName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentUser, thingName, description, location);
    }
}