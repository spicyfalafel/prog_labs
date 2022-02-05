public class Animal extends AliveCreature {
    Animal(String name, Gender gender, Location currLoc) {
        super(name, gender, currLoc);
    }
    private AdditionalAnimalInformation info = new AdditionalAnimalInformation("",4,10,10);
    public AdditionalAnimalInformation getAdditionalInfo(){
        return info;
    }
    public class AdditionalAnimalInformation {
        private int legsNumber;
        private double weight;
        //в см
        private double height;
        private String kind;
        public int getLegsNumber() {
            return legsNumber;
        }

        public double getWeight() {
            return weight;
        }

        public double getHeight() {
            return height;
        }
        public String getKind(){
            return kind;
        }

        @Override
        public String toString() {
            return this.kind + " " + this.legsNumber + " " + this.weight + " " + this.height;
        }

        public AdditionalAnimalInformation(String kind, int legs, double weight, double height){
            this.kind = kind;
            this.legsNumber = legs;
            this.weight = weight;
            this.height = height;
        }
    }
}