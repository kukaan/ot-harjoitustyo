package lottokone.domain;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LottokoneServiceTest {
    Random random;
    Random floor;
    Random ceiling;
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
        floorService = new LottokoneService(floor);
        ceilingService = new LottokoneService(ceiling);
        randomService = new LottokoneService(random);
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
}
