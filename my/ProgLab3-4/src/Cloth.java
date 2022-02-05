import java.util.Objects;

public class Cloth extends ThingToInteract{
    private boolean isWeared = false;
    private ClothType type;
    Cloth(String thingName, String description, ClothType type) {
        super(thingName, description);
        this.type = type;
    }


    public boolean isTypeOf(ClothType type){
        return type.equals(this.type);
    }

    ClothType getClothType(){
        return this.type;
    }

    enum ClothType{
        HEADDRESS("головной убор"),
        BODY("одежда на туловище"),
        PANTS("штаны");
        String rus;
        ClothType(String t){
            this.rus = t;
        }
    }

    @Override
    public void interaction(AliveCreature user) {
        // носить одежду может только объект класса с интерфейсом WearClothes!
        if(user instanceof WearClothes){
            if(isWeared){
                System.out.println("Данный " + type.rus + " уже на ком-то надет!");
            }else{
                ( (WearClothes)user).wear(this);
                isWeared = true;
            }
        }else{
            System.out.println("Не может носить одежду!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cloth cloth = (Cloth) o;
        return isWeared == cloth.isWeared &&
                type == cloth.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isWeared, type);
    }
}