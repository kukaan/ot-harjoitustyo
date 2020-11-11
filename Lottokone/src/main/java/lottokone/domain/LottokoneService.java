package lottokone.domain;

import java.util.Arrays;
import java.util.Random;

public class LottokoneService {
    
    Random random;
    
    int drawSize; // how many numbers will be chosen/drawn
    int amountOfChoosableNumbers; // how many numbers there are to choose/draw from

    public LottokoneService(Random random) {
        this.random = random;
        
        drawSize = 7;
        amountOfChoosableNumbers = 40;
    }

    public int[] draw() {
        boolean[] removed = new boolean[amountOfChoosableNumbers];
        int[] allDrawn = new int[drawSize];
        for (int i = 0; i < drawSize; i++) {
            int oneDrawn = random.nextInt(amountOfChoosableNumbers);
            while (removed[oneDrawn]) {
                oneDrawn = random.nextInt(amountOfChoosableNumbers);
//                System.out.println(oneDrawn);
            }
            removed[oneDrawn] = true;
            allDrawn[i] = oneDrawn + 1;
        }
        Arrays.sort(allDrawn);
        return allDrawn;
    }
    
}
