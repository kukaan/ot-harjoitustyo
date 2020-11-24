package lottokone.domain;

import java.util.Arrays;
import java.util.Random;
import lottokone.dao.UserDao;

public class LottokoneService {
    
    private Random random;
    private UserDao userDao;
    private User loggedUser;
    
    private int drawSize; // how many numbers will be chosen/drawn
    private int amountOfChoosableNumbers; // how many numbers there are to choose/draw from

    public LottokoneService(Random random, UserDao userDao) {
        this.random = random;
        this.userDao = userDao;
        
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
    
    public boolean create(String username) {
        User u = userDao.create(new User(username));
        return u != null;
    }
    
    public boolean login(String username) {
        loggedUser = userDao.findByName(username);
        return loggedUser != null;
    }
    
    public void logout() {
        loggedUser = null;
    }
    
    public User getLoggedUser() {
        return loggedUser;
    }
}
