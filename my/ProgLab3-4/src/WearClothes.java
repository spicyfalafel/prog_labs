public interface WearClothes {
    // то, как много вещей может быть надето
    // пусть зависит от конкретного класса
    void wear(Cloth cloth);
    void takeOff(Cloth.ClothType clothType);
}