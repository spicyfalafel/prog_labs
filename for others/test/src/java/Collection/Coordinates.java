package Collection;


public class Coordinates {

    private Double x; //Поле не может быть null
    private float y;


    public Coordinates(Double x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }


    public Double getX() {
        return x;
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ')';
    }

    public float getY() {
        return y;
    }
}
