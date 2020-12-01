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
        int[] numbersToAdd;
        try {
            numbersToAdd = stringToValidArrayOfNumbers(input);
        } catch (Exception e) {
            return false;
        }
        Arrays.sort(numbersToAdd);
        if (numbersHaveDuplicates(numbersToAdd)) {
            return false;
        }
        if (accountHasNumbers(numbersToAdd)) { 
            return false;
        }
        loggedUser.addNumbers(numbersToAdd);
        return true;
    }
    
    private int[] stringToValidArrayOfNumbers(String input) throws Exception {
        int[] array = new int[drawSize];
        String[] s = input.split(",");
        if (s.length != drawSize) { 
            throw new Exception();
        }
        for (int i = 0; i < drawSize; i++) {
            int number = -1;
            try {
                number = Integer.valueOf(s[i]);
            } catch (NumberFormatException e) {
                throw e;
            }
            if (number < 1 || number > rangeSize) { 
                throw new Exception();
            }
            array[i] = number;
        }
        return array;
    }
    
    private boolean numbersHaveDuplicates(int[] numbersToAdd) {
        for (int i = 0; i < drawSize; i++) {
            for (int j = 0; j < drawSize; j++) {
                if (i == j) {
                    continue;
                }
                if (numbersToAdd[i] == numbersToAdd[j]) { 
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean accountHasNumbers(int[] numbers) {
        for (int[] accountNumbers : loggedUser.getNumbersList()) {
            for (int i = 0; i < drawSize; i++) {
//                System.out.println("acc: "+accountNumbers[i]+", new:"+numbers[i]);
                if (accountNumbers[i] !=  numbers[i]) {
                    break;
                }
                if (i == drawSize - 1) {
//                    System.out.println("accountHasNumbers");
                    return true;
                }
            }
        }
        return false;
    }
}
