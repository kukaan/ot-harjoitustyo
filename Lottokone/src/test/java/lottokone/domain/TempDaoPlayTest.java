package lottokone.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lottokone.dao.TemporaryUserDao;
import lottokone.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TempDaoPlayTest {
    Random random;
    Random floor;
    Random ceiling;
    UserDao userDao;
    LottokoneService floorService;
    LottokoneService ceilingService;
    LottokoneService service;
    ArrayList expected;
    

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
    
    public TempDaoPlayTest() {
    }
    
    @Before
    public void setUp() {
        floor = new FloorRandom();
        ceiling = new CeilingRandom();
        random = new Random();
        userDao = new TemporaryUserDao();
        floorService = new LottokoneService(floor, userDao);
        ceilingService = new LottokoneService(ceiling, userDao);
        service = new LottokoneService(random, userDao);
        
        userDao.createUser("asdf");
        service.login("asdf");
        service.add("1,2,3,4,5,6,7");
        service.add("17,16,15,14,13,12,11");
        
        expected = new ArrayList<>();
        expected.add(new Numbers(new int[] {1,2,3,4,5,6,7}));
    }
    
    @Test
    public void allTicketsInputSelectsCorrectly() {
        expected.add(new Numbers(new int[] {11,12,13,14,15,16,17}));
        assertThat(service.selectTickets("0"), is(equalTo(expected)));
    }
    
    @Test
    public void oneTicketInputSelectsCorrectly() {
        assertThat(service.selectTickets("1"), is(equalTo(expected)));
    }
    
    @Test
    public void playingCostsAreCorrect() {
        service.buyTickets(3);
        assertTrue(service.getLoggedUser().getMoneyLost() == 3 * service.getTicketPrice());
    }
    
    @Test
    public void winsAreAddedCorrectly() {
        List<Integer> winnings = new ArrayList<>();
        winnings.add(1);
        winnings.add(10000000);
        service.addWinnings(winnings);
        assertTrue(service.getLoggedUser().getMoneyWon() == winnings.get(0) + winnings.get(1));
    }
}
