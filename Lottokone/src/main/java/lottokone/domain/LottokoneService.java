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

    /**
     * Start the service.
     * @param random    an RNG
     * @param userDao   DAO for User objects
     */
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
    
    
    /**
     * Generates random numbers based on the current settings.
     * @return valid random numbers
     */
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
    
    /**
     * Creates a new user account.
     * @param username  user input
     * @return operationSuccessful
     */
    public boolean createUser(String username) {
        return userDao.createUser(username);
    }
    
    /**
     * Signs into an existing user account.
     * @param username  user input
     * @return operationSuccessful
     */
    public boolean login(String username) {
        loggedUser = userDao.findByName(username);
        return loggedUser != null;
    }
    
    /**
     * Signs out of the currently logged in user account.
     */
    public void logout() {
        loggedUser = null;
    }
    
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Adds and saves numbers/ticket to user through multiple steps of validation.
     * @param input user input
     * @return operationSuccessful
     */
    public boolean add(String input) {
        List<Integer> numbersToAdd;
        try {
            numbersToAdd = stringToListOfNumbers(input);
        } catch (Exception e) {
            return false;
        }
        Collections.sort(numbersToAdd);
        if (numbersHaveDuplicates(numbersToAdd)) {
            return false;
        }
        if (accountHasSameNumbers(numbersToAdd)) { 
            return false;
        }
        loggedUser.addNumbers(new Numbers(numbersToAdd));
        return true;
    }
    
    /**
     * Transforms user input into a list of numbers which are valid in amount (equals drawSize) 
     * and range (equals rangeSize).
     * @param input user input
     * @return  a valid list of numbers
     * @throws Exception 
     */
    public List<Integer> stringToListOfNumbers(String input) throws Exception {
        return stringToListOfNumbers(input, rangeSize, drawSize, drawSize);
    }
    
    /**
     * Transforms user input into a list of numbers which are valid in amount 
     * and range.
     * @param input user input
     * @param maxValue  maximum value of any given number
     * @param minAmount minimum amount of numbers
     * @param maxAmount maximum amount of numbers
     * @return  a valid list of numbers
     * @throws Exception 
     */
    private List<Integer> stringToListOfNumbers(String input, int maxValue, int minAmount, int maxAmount) throws Exception {
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
            if (number < 1 || number > maxValue) { 
                throw new Exception();
            }
            list.add(number);
        }
        return list;
    }
    
    /**
     * Checks for duplicates in numbers.
     * @param numbers
     * @return hasDuplicates
     */
    private boolean numbersHaveDuplicates(List<Integer> nums) {
        for (int i = 0; i < drawSize; i++) {
            for (int j = 0; j < drawSize; j++) {
                if (i == j) {
                    continue;
                }
                if (nums.get(i).equals(nums.get(j))) { 
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the currently logged in user has already saved the given numbers.
     * @param numbers
     * @return hasSameNumbers
     */
    private boolean accountHasSameNumbers(List<Integer> numbers) {
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
    
    /**
     * Select specific saved numbers/tickets by id input separated by commas 
     * or select all numbers/tickets by input 0.
     * @param ticketIdsInput    user input
     * @return list of tickets
     */
    public List<Numbers> selectTickets(String ticketIdsInput) {
        List<Numbers> numbersList = loggedUser.getNumbersList();
        if (ticketIdsInput.equals("0")) {
            return numbersList;
        }
        List<Integer> ticketIds;
        try {
            ticketIds = stringToListOfNumbers(ticketIdsInput,
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
    
    /**
     * Counts the amount of matching numbers on each ticket in the given list.
     * @param drawn the winning numbers
     * @param selectedTickets   the played numbers
     * @return amount of hits on each ticket played
     */
    public List<Integer> countHitsOnTickets(Numbers drawn, List<Numbers> selectedTickets) {
        List<Integer> hitsOnTickets = new ArrayList<>(selectedTickets.size());
        for (Numbers ticket : selectedTickets) {
            hitsOnTickets.add(countTicketHits(drawn, ticket));
        }
        return hitsOnTickets;
    }
    
    /**
     * Counts the amount of matching numbers in a single ticket.
     * @param drawn
     * @param ticket
     * @return amount of hits on this ticket
     */
    private int countTicketHits(Numbers drawn, Numbers ticket) {
        int hits = 0;
        for (int i = 0; i < drawn.getNumbers().size(); i++) {
            if (drawn.getNumbers().contains(ticket.getNumbers().get(i))) {
                hits++;
            }
        }
        return hits;
    }
    
    /**
     * Calculates the total cost of given amount of tickets 
     * and adds them to the user.
     * @param tickets   amount of tickets
     * @return sum of costs
     */
    public int buyTickets(int tickets) {
        int costs = tickets * ticketPrice;
        loggedUser.addLoss(costs);
        return costs;
    }

    /**
     * Calculates the winnings on each ticket based on the amount of hits on each.
     * @param hitsOnTickets amount of hits on each ticket
     * @return winnings on each ticket
     */
    public List<Integer> calculateWinnings(List<Integer> hitsOnTickets) {
        List<Integer> winningsPerTicket = new ArrayList<>();
        for (int i = 0; i < hitsOnTickets.size(); i++) {
            int hits = hitsOnTickets.get(i);
            winningsPerTicket.add(i, prizes[drawSize - hits]);
        }
        return winningsPerTicket;
    }

    /**
     * Sums the given winnings and adds them to the user.
     * @param winnings  a List of money prizes won by each ticket
     * @return sum of winnings
     */
    public int addWinnings(List<Integer> winnings) {
        int winSum = winnings.stream().mapToInt(win -> win).sum();
        loggedUser.addWin(winSum);
        return winSum;
    }
}
