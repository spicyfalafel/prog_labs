abstract public class Essence{

    protected Location currentLocation;
    protected String description;
    protected boolean descriptionShown = false;

    @Override
    public String toString() {
        return "сущность которая находится в " + this.currentLocation.getPlaceName();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
    public void descriptionInConsole(){
        System.out.println("\nОПИСАНИЕ:\n" + description);
    }
}