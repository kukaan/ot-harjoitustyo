package lottokone.domain;

import java.util.Random;
import lottokone.dao.TemporaryUserDao;
import lottokone.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LottokoneServiceTest {
    Random random;
    Random floor;
    Random ceiling;
    UserDao userDao;
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
    
    public LottokoneServiceTest() {
    }
    
    @Before
    public void setUp() {
        floor = new FloorRandom();
        ceiling = new CeilingRandom();
        random = new Random();
        userDao = new TemporaryUserDao();
        floorService = new LottokoneService(floor, userDao);
        ceilingService = new LottokoneService(ceiling, userDao);
        randomService = new LottokoneService(random, userDao);
        
        userDao.create(new User("asdf"));
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
        assertTrue(randomService.create("baba"));
    }
    
    @Test
    public void createReturnsFalseIfDaoReturnsNull() {
        assertFalse(randomService.create("asdf"));
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
}
