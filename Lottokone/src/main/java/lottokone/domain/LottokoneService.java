package lottokone.domain;

import java.util.Arrays;
import java.util.Random;
import lottokone.dao.UserDao;

public class LottokoneService {
    
    private Random random;
    private UserDao userDao;
    private User loggedUser;
    
    private int drawSize; // how many numbers will be chosen/drawn
    private int rangeSize; // how many numbers there are to choose/draw from

    public LottokoneService(Random random, UserDao userDao) {
        this.random = random;
        this.userDao = userDao;
        
        drawSize = 7;
        rangeSize = 40;
    }

    public int getDrawSize() {
        return drawSize;
    }

    public int getRange() {
        return rangeSize;
    }
    
    

    public int[] draw() {
        boolean[] removed = new boolean[rangeSize];
        int[] allDrawn = new int[drawSize];
        for (int i = 0; i < drawSize; i++) {
            int oneDrawn = random.nextInt(rangeSize);
            while (removed[oneDrawn]) {
                oneDrawn = random.nextInt(rangeSize);
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

    public boolean add(String input) {
        
        // process and validate the input
        int[] numbersToAdd = new int[drawSize];
        String[] s = input.split(",");
        if (s.length != drawSize) return false;
        for (int i = 0; i < drawSize; i++) {
            int number = -1;
            try {
                number = Integer.valueOf(s[i]);
            } catch (Exception e) {
                return false;
            }
            if (number < 1 || number > rangeSize) return false;
            numbersToAdd[i] = number;
        }
        Arrays.sort(numbersToAdd);
        for (int i = 0; i < drawSize; i++) {
            for (int j = 0; j < drawSize; j++) {
                if (i == j) continue;
                if (numbersToAdd[i] == numbersToAdd[j]) return false;
            }
        }
        
        if (accountHasNumbers(numbersToAdd)) return false;
        
        loggedUser.addNumbers(numbersToAdd);
        return true;
    }
    
    private boolean accountHasNumbers(int[] numbers) {
        for (int[] accountNumbers : loggedUser.getNumbersList()) {
            for (int i = 0; i < drawSize; i++) {
                if (accountNumbers[i] !=  numbers[i]) {
                    break;
                }
                return true;
            }
        }
        return false;
    }
}
