abstract public class AliveCreature extends Essence implements CanInteract, Alive{

    // name - название вида, типа живого существа
    protected String name = getClass().getName();

    private ThingToInteract savedThing;
    // Стамина, пол есть только у живых существ.
    protected final int MAX_STAMINA = 100;
    protected Gender gender;

    //коэффициент для стамины: чем больше, тем больше энергии требуют действия
    private final int STAMINA_СOEFFICIENT = 5;
    AliveCreature(String name, Gender gender, Location currLoc){
        this.name = name;
        this.gender = gender;
        this.currentLocation = currLoc;
    }

    @Override
    public void interact(ThingToInteract thing) {
        thing.mainInteraction(this);
    }

    // Усталость решает, может ли существо что-то делать
    // TIRED при стамине равной 0
    // при TIRED существо может не может взаимодействовать с вещами, но может говорить
    // и тд
    protected enum State{
        TIRED,
        NORMAL;
    }
    //
    public void saveThing(ThingToInteract thing) throws MyNullPointerException{
        try{
            if (thing == null) throw new MyNullPointerException(getClass().getName());
            if(savedThing != null){
                System.out.println("У "+ getName() + " уже есть " + savedThing.getThingName());
                removeSavedThing();
            }
            System.out.println("Теперь у " + getName() + " есть " + thing.getThingName());
            savedThing = thing;
        }catch (MyNullPointerException e){
            System.out.println(e.getMessage());
        }

    }
    public void removeSavedThing(){
        System.out.println(getName() + " избавляется от " + savedThing);
        savedThing = null;
    }

    public Gender getGender() {
        return gender;
    }
    public boolean isTired(){
        return currentState.equals(State.TIRED);
    }
    protected State currentState = State.NORMAL;

    protected int stamina = MAX_STAMINA;

    @Override
    public void sleep(){
        if (isTired()) {
            this.stamina = MAX_STAMINA;
            currentState = State.NORMAL;
            System.out.println(getName() + " спит. Энергия восстановилась до " + MAX_STAMINA + ".");
        }else{
            String heShe = getGender().equals(Gender.FEMALE) ? "её" : "его";
            System.out.println(getName() + " не может поспать, так как " + heShe +
                    " энергия больше нуля(" + this.stamina + ").");
        }
    }
    @Override
    public void say(String msg) {
        System.out.println(getName() + " издает звук: " + msg);
        changeStaminaBy(-1);
    }

    @Override
    public void eat(ThingToInteract food) {
        System.out.println(getName() + " съе" +(gender.equals(Gender.FEMALE)? "ла " : "л ") +
                food.getThingName());
        changeStaminaBy(20);
    }


    protected void changeStaminaBy(int i) {
        i=i*STAMINA_СOEFFICIENT;
        if(i>=0){
            if(stamina+i <= MAX_STAMINA ){
                if(isTired()){
                    setCurrentState(State.NORMAL);
                }
                stamina+=i;
            }else if(stamina<MAX_STAMINA){
                i = MAX_STAMINA - stamina;
                stamina+=i;
            }else{
                i=0;
            }
            System.out.print("Энергии восстановлено: " + i);
        }else{
            if(stamina+i <= 0){
                stamina=0;
                setCurrentState(State.TIRED);
            }else{
                stamina+=i;
            }
            System.out.print("Снижение энергии: " + i);
        }
        System.out.println(", количество энергии у " +
                getName() + ": " + stamina);
    }

    protected void setCurrentState(State currentState) {
        this.currentState = currentState;
        System.out.println("Текущее состояние у " + name + ": " + currentState.toString());
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void goToLocation(Location location) {
        if(!this.descriptionShown){
            descriptionInConsole();
            descriptionShown = true;
        }
        if(location.equals(this.currentLocation)){
            System.out.println("не может пойти к " + location.getPlaceName() + ": " +
                    (getGender().equals(Gender.FEMALE) ? "она" : "он") + " уже там!");
        }else{
            currentLocation = location;
            System.out.println(name + " приш" + (gender.equals(Gender.FEMALE)? "ла" : "ел")
                    + " в " + currentLocation.getPlaceName());
            changeStaminaBy(-10);
            location.describeArea();
        }
    }

    public String getName() {
        return name;
    }
    //может понадобятся
    public int getMAX_STAMINA() {
        return MAX_STAMINA;
    }
    public State getCurrentState() {
        return currentState;
    }
    public int getStamina() {
        return stamina;
    }

    @Override
    public String toString() {
        return getClass().getName() + ", " + gender.rus() + " (" + currentLocation.getPlaceName() + ")";
    }
}