import com.itmo.collection.dragon.classes.*;
import com.itmo.database.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

public class DbTesting {
    static DatabaseManager db;
    static Dragon testDragon = new Dragon(
            "test",
            new Coordinates(1,1),
            5,
            5,
            DragonType.AIR,
            DragonCharacter.CUNNING,
            new Person(
                    "killer",
                    LocalDateTime.now(),
                    Color.BROWN,
                    Country.RUSSIA,
                    new Location(
                            1,
                            2L,
                            5f,
                            "locationname"
                    )
            )
    );

    static int numOfRowsBeforeTests;

    @BeforeAll
    static void setUp(){
        db = new DatabaseManager();
        testDragon.setOwnerName("admin");
    }

    @Order(1)
    @Test
    void getCollectionWorks(){
        try {
            Set<Dragon> dragons = db.getCollectionFromDatabase();
            numOfRowsBeforeTests = dragons.size();
            for (Dragon d : dragons){
                System.out.println(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Order(2)
    @Test
    void insertDragonTest(){
        Assertions.assertTrue(db.insertDragon(testDragon));
    }


    //sometimes it doesn't work don't know why
    @Order(3)
    @Test
    void getIdOfDragonWorks(){
        long id = db.getIdOfDragon(testDragon);
        Assertions.assertNotEquals(0, id);
    }
    @Order(4)
    @Test
    void deleteDragonByIdWorks(){
        long id = db.getIdOfDragon(testDragon);
        Assertions.assertTrue(db.deleteDragonById(id));
    }

    @AfterAll
    @Test
    static void testsDidntChangeDatabase(){
        Set<Dragon> dragons = null;
        try {
            dragons = db.getCollectionFromDatabase();
            Assertions.assertEquals(numOfRowsBeforeTests, dragons.size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
