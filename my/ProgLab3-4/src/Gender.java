public enum Gender{
    MALE("мужской пол"),
    FEMALE("женский пол");
    String t;
    String rus(){
        return t;
    }
    Gender(String t){
        this.t = t;
    }
}