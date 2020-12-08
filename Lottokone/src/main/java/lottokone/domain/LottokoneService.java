package lottokone.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lottokone.dao.UserDao;

public class LottokoneService {
    
    private Random random;
    private UserDao userDao;
    private User loggedUser;
    
    private int drawSize; // how many numbers will be chosen/drawn
    private int rangeSize; // how many numbers there are to choose/draw from
    
    private int ticketPrice;
    private int[] prizes;

    public LottokoneService(Random random, UserDao userDao) {
        this.random = random;
        this.userDao = userDao;
        
        drawSize = 7;
        rangeSize = 40;
        
        ticketPrice = 100;
        prizes = new int[] {130000000, 200000, 5000, 1000, 0, 0, 0, 0};
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
        List<Integer> numbersToAdd;
        try {
            numbersToAdd = stringToValidListOfNumbers(input, rangeSize, drawSize, drawSize);
        } catch (Exception e) {
            return false;
        }
        Collections.sort(numbersToAdd);
        if (numbersHaveDuplicates(numbersToAdd)) {
            return false;
        }
        if (accountHasNumbers(numbersToAdd)) { 
            return false;
        }
        loggedUser.addNumbers(new Numbers(numbersToAdd));
        return true;
    }
    
    private List<Integer> stringToValidListOfNumbers(String input, int roof, int minAmount, int maxAmount) throws Exception {
        List<Integer> list = new ArrayList<>(minAmount);
        String[] s = input.split(",");
        if (s.length < minAmount || s.length > maxAmount) {
            throw new Exception();
        }
        for (int i = 0; i < s.length; i++) {
            int number = -1;
            try {
                number = Integer.valueOf(s[i]);
            } catch (NumberFormatException e) {
                throw e;
            }
            if (number < 1 || number > rangeSize) { 
                throw new Exception();
            }
            list.add(number);
        }
        return list;
    }
    
    private boolean numbersHaveDuplicates(List<Integer> numbersToAdd) {
        for (int i = 0; i < drawSize; i++) {
            for (int j = 0; j < drawSize; j++) {
                if (i == j) {
                    continue;
                }
                if (numbersToAdd.get(i).equals(numbersToAdd.get(j))) { 
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean accountHasNumbers(List<Integer> numbers) {
        for (Numbers accountNumbers : loggedUser.getNumbersList()) {
            for (int i = 0; i < drawSize; i++) {
//                System.out.println("acc: "+accountNumbers[i]+", new:"+numbers[i]);
                if (!accountNumbers.getNumbers().get(i).equals(numbers.get(i))) {
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

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    
    public List<Numbers> selectTickets(String ticketIdsInput) {
        List<Numbers> numbersList = loggedUser.getNumbersList();
        if (ticketIdsInput.equals("0")) {
            return numbersList;
        }
        List<Integer> ticketIds;
        try {
            ticketIds = stringToValidListOfNumbers(ticketIdsInput, 
                    numbersList.size(), 1, numbersList.size());
        } catch (Exception e) {
            return null;
        }
        
        List<Numbers> selectedTickets = new ArrayList<>();
        for (Integer id : ticketIds) {
            selectedTickets.add(numbersList.get(id - 1));
        }
        return selectedTickets;
    }
    
    public List<Integer> play(Numbers drawn, List<Numbers> selectedTickets) {
        List<Integer> hitsOnTickets = new ArrayList<>(selectedTickets.size());
        for (Numbers ticket : selectedTickets) {
            hitsOnTickets.add(countTicketHits(drawn, ticket));
        }
        return hitsOnTickets;
    }

    // todo: fix this:
    // The winning numbers are [3, 7, 13, 17, 19, 28, 39]
    // [1, 2, 3, 4, 5, 6, 7]: 0 matches wins you 0.0€
    // The winning numbers are [2, 10, 14, 18, 36, 37, 40]
    // [21, 22, 23, 24, 25, 26, 27]: 5 matches wins you 50.0€
    private int countTicketHits(Numbers drawn, Numbers ticket) {
        int hits = 0;
        for (int i = 0; i < drawn.getNumbers().size(); i++) {
            if (drawn.getNumbers().contains(ticket.getNumbers().get(i))) {
                hits++;
            }
        }
        return hits;
    }
    
    public int buyTickets(int tickets) {
        int costs = tickets * ticketPrice;
        loggedUser.addLoss(costs);
        return costs;
    }

    public List<Integer> countWinnings(List<Integer> hitsOnTickets) {
        List<Integer> winningsPerTicket = new ArrayList<>();
        for (int i = 0; i < hitsOnTickets.size(); i++) {
            int hits = hitsOnTickets.get(i);
            winningsPerTicket.add(i, prizes[drawSize - hits]);
        }
        return winningsPerTicket;
    }

    public int addWinnings(List<Integer> winnings) {
        int winSum = winnings.stream().mapToInt(win -> win).sum();
        loggedUser.addWin(winSum);
        return winSum;
    }
}
