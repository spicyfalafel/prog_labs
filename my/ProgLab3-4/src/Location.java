import java.util.ArrayList;

public class Location {
    private boolean descriptionShown = false;
    private String placeName;
    private String description;
    private ArrayList<ThingToInteract> thingsToInteract;

    Location(String placeName, String description){
        this.placeName = placeName;
        this.description = description;
        thingsToInteract = new ArrayList<>();
        descriptionShown = false;
    }
    String getPlaceName(){
        return placeName;
    }

    ThingToInteract getThingWithName(String thingName){
        for (ThingToInteract thing :
                thingsToInteract) {
            if (thing.getThingName().equals(thingName)) {
                return thing;
            }
        }
        return null;
    }
    void removeThingToInteract(ThingToInteract thing){
        thingsToInteract.remove(thing);
        thingsToInteract.add(null);
    }
    void addThingsToInteract(ThingToInteract[] things){
        for (ThingToInteract thing :
                things) {
            thingsToInteract.add(thing);
            thing.setLocation(this);
        };
    }
    void addThingToInteract(ThingToInteract thing){
        thingsToInteract.add(thing);
        thing.setLocation(this);
    }

    ArrayList<ThingToInteract> getThingsToInteract() {
        return thingsToInteract;
    }

    void clearAllThingsInLocation(){
        thingsToInteract.clear();
    }

    boolean hasThing(ThingToInteract thing){
        for (ThingToInteract t :
                this.thingsToInteract) {
            if (t.equals(thing)){
                return true;
            }
        }
        return false;
    }

    public void setThingsToInteract(ArrayList<ThingToInteract> things) {
        this.thingsToInteract.addAll(things);
        for (ThingToInteract thing :
                this.thingsToInteract) {
            thing.setLocation(this);
        }
    }

    void describeArea(){
        if(this.descriptionShown) return;
        System.out.println("Описание локации: " + description);
        int length = thingsToInteract == null? 0 : thingsToInteract.size();
        if(length>0){
            System.out.println("Здесь есть предметы: ");
            for (int i = 0; i<length; i++) {
                System.out.println("["+ (i+1) +"] "+ thingsToInteract.get(i).getThingName());
                System.out.println("Описание предмета: " + thingsToInteract.get(i).getDescription());
            }
        }
        this.descriptionShown = true;
    }
}