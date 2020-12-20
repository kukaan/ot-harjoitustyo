package lottokone.domain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import lottokone.dao.SQLiteUserDao;
import lottokone.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;

public class SQLiteDaoServiceTest {
    Random random;
    Random floor;
    Random ceiling;
    UserDao userDao;
    final File dbFile = new File("test.db");
    LottokoneService floorService;
    LottokoneService ceilingService;
    LottokoneService randomService;
    

    public class FloorRandom extends Random {
        int i = 0;
        @Override
        public int nextInt(int n) {
            return i++;
        }
    }
    
    public class CeilingRandom extends Random {
        int i = 1;
        @Override
        public int nextInt(int n) {
            return n - i++;
        }
    }
    
    public SQLiteDaoServiceTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        floor = new FloorRandom();
        ceiling = new CeilingRandom();
        random = new Random();
        dbFile.createNewFile();
        userDao = new SQLiteUserDao("test.db");
        floorService = new LottokoneService(floor, userDao);
        ceilingService = new LottokoneService(ceiling, userDao);
        randomService = new LottokoneService(random, userDao);
        
        userDao.createUser("asdf");
    }
    
    @After
    public void tearDown() {
        dbFile.delete();
    }

    @Test
    public void lowestNumbersDrawWorks() {
        int[] expected = new int[7];
        for (int i = 0; i < 7; i++) {
            expected[i] = i+1;
        }
        assertThat(floorService.draw(), is(equalTo(expected)));
    }
    
    @Test
    public void highestNumbersDrawWorks() {
        int[] expected = new int[7];
        for (int i = 0; i < 7; i++) {
            expected[i] = i+34;
        }
        assertThat(ceilingService.draw(), is(equalTo(expected)));
    }
    
    @Test
    public void noDuplicatesInDraw() {
        for (int repetition = 0; repetition < 10; repetition++) {
            int[] drawn = randomService.draw();
            for (int i = 0; i < 6; i++) {
                assertThat(drawn[i], is(not(equalTo(drawn[i+1]))));
            }
        }
    }
    
    @Test
    public void drawnNumbersAreWithinLimits() {
        for (int repetition = 0; repetition < 10; repetition++) {
            int[] drawn = randomService.draw();
            for (int i = 0; i < 7; i++) {
                assertTrue(drawn[i] > 0 && drawn[i] < 41);
            }
        }
    }
    
    @Test
    public void createReturnsTrueIfDaoReturnsUser() {
        assertTrue(randomService.createUser("baba"));
    }
    
    @Test
    public void createReturnsFalseIfDaoReturnsNull() {
        assertFalse(randomService.createUser("asdf"));
    }
    
    @Test
    public void loginReturnsTrueIfDaoReturnsUser() {
        assertTrue(randomService.login("asdf"));
    }
    
    @Test
    public void loginReturnsFalseIfDaoReturnsNull() {
        assertFalse(randomService.login("baba"));
    }
    
    @Test
    public void getLoggedUserReturnsLoggedUser() {
        randomService.login("asdf");
        assertThat(randomService.getLoggedUser().getName(), is(equalTo("asdf")));
    }
    
    @Test
    public void logoutMakesLoggedUserNull() {
        randomService.login("asdf");
        randomService.logout();
        assertThat(randomService.getLoggedUser(), is(equalTo(null)));
    }
    
    /*
    @Test
    public void validNumbersInputCanBeAdded() {
        randomService.login("asdf");
        randomService.add("40,1,2,3,4,5,6");
        int[] expected = {1,2,3,4,5,6,40};
        List<Numbers> numbersList = randomService.getLoggedUser().getNumbersList();
        assertThat(numbersList.get(0), is(equalTo(expected)));
    }
    
    @Test
    public void twoDifferentValidNumbersInputsCanBeAdded() {
        randomService.login("asdf");
        randomService.add("39,1,2,3,4,5,6");
        int[] expected0 = {1,2,3,4,5,6,39};
        randomService.add("40,1,2,3,4,5,6");
        int[] expected1 = {1,2,3,4,5,6,40};
        List<Numbers> numbersList = randomService.getLoggedUser().getNumbersList();
//        System.out.println(Arrays.toString(expected0));
//        System.out.println(numbersList.get(0));
        assertThat(numbersList.get(0), is(equalTo(expected0)));
        assertThat(numbersList.get(1), is(equalTo(expected1)));
    }
    
    
    @Test
    public void numbersAlreadyOnAccountCannotBeAdded() {
        randomService.login("asdf");
        randomService.add("40,1,2,3,4,5,6");
        assertFalse(randomService.add("6,40,1,2,3,4,5"));
    }
    */
    
    @Test
    public void numbersOutOfRangeCannotBeAdded() {
        randomService.login("asdf");
        assertFalse(randomService.add((1 + randomService.getRange()) + ",1,2,3,4,5"));
        assertFalse(randomService.add("0,1,2,3,4,5"));
    }
    
    @Test
    public void wrongAmountOfNumbersCannotBeAdded() {
        randomService.login("asdf");
        assertFalse(randomService.add("1,2,3,4,5,6,7,8"));
        assertFalse(randomService.add("1,2,3,4,5,6"));
    }
}
