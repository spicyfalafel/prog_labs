import com.itmo.collection.DragonWithStringFields;
import com.itmo.collection.dragon.classes.*;
import com.itmo.utils.FieldsValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AddFieldsValidationTesting {

    static FieldsValidator validator;
    static DragonWithStringFields d;
    @BeforeAll
    static void start(){

        d = new DragonWithStringFields();
        String name = "5";
        String x = "5";
        String y = "5";
        String wingspan = "5";
        String age = "5";
        DragonType type = null;
        DragonCharacter character = DragonCharacter.GOOD;
        String killerName = "";
        Country nationality = null;
        Color hairColor = null;
        String date = "";
        String locName = "";
        String killerX = "";
        String killerY = "";
        String killerZ = "";

        d.setName(name);
        d.setX(x);
        d.setY(y);
        d.setWingspan(wingspan);
        d.setAge(age);
        d.setKillerName(killerName);
        d.setBirthday(date);
        d.setLocName(locName);
        d.setKillerX(killerX);
        d.setKillerY(killerY);
        d.setKilllerZ(killerZ);
        d.setType(type);
        d.setCharacter(character);
        d.setHairColor(hairColor);
        d.setNationality(nationality);
        validator = new FieldsValidator(d);
    }

    @Test
    void isDateTesting(){
        Assertions.assertTrue(validator.isDate("2000-01-01"));

        Assertions.assertTrue(validator.isDate("2001-11-11"));

        Assertions.assertTrue(validator.isDate("0001-01-01"));

        Assertions.assertTrue(validator.isDate("2020-01-01"));
    }

    @Test
    void dIsFine(){
        Assertions.assertTrue(validator.isMainFieldsOK());
        Assertions.assertTrue(validator.isKillerFieldsEmpty());
    }
}
