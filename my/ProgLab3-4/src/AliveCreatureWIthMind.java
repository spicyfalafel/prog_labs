abstract public class AliveCreatureWIthMind extends AliveCreature {
    // класс для существ, которые могут говорить, у которых есть разум
    // при этом это не обязательно человекоподобные существа
    AliveCreatureWIthMind(String name, Gender gender, Location currLoc) {
        super(name, gender, currLoc);
    }

    @Override
    public void say(String msg) {
        if (msg.isEmpty()){
            System.out.println(name + " молчит.");
        }else{
            System.out.println(name + " говорит: " + msg);
            changeStaminaBy(-1);
        }
    }

    public void enjoyNature(Nature nature){
        System.out.println(getName() + " наслаждается природным объектом: "
                + nature.getThingName());
    }
}