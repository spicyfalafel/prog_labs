import com.itmo.collection.dragon.classes.Color;
import com.itmo.collection.dragon.classes.Country;
import com.itmo.collection.dragon.classes.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DateTesting {

    /*
    public String getBirthdayInFormat() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(birthday.toLocalDate());
    }

    public void setBirthdayInFormat(String yyyy_mm_dd){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        birthday = LocalDateTime.parse(yyyy_mm_dd, formatter);
    }
     */

    static Person test;

    @BeforeAll
    static void setUp(){
        test =  new Person("John", Color.BROWN, Country.RUSSIA, null);
    }


    @Test
    public void setBirthdayInFormatShouldWorkAsExpected(){
        String dateStr = "1999-01-01";
        test.setBirthdayInFormat(dateStr);
        String birthday = test.getBirthdayInFormat();
        Assertions.assertEquals("1999-01-01", birthday);
    }
}
