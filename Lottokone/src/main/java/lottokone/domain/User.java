package lottokone.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private final String name;
    private List<Numbers> numbersList;
    private int moneyWon;
    private int moneyLost;
    
    /**
     * A user account.
     * @param id    index in database
     * @param name  just a name
     * @param moneyWon  amount of money won so far
     * @param moneyLost amount of money lost so far
     */
    public User(int id, String name, int moneyWon, int moneyLost) {
        this(id, name, null, moneyWon, moneyLost);
    }
    
    /**
     * A user account.
     * @param id    index in database
     * @param name  just a name
     * @param numbersList   a List for the numbers of this User to be saved in
     * @param moneyWon  amount of money won so far
     * @param moneyLost amount of money lost so far
     */
    public User(int id, String name, List<Numbers> numbersList, int moneyWon, int moneyLost) {
        this.id = id;
        this.name = name;
        this.numbersList = numbersList;
        this.moneyWon = moneyWon;
        this.moneyLost = moneyLost;
    }

    public String getName() {
        return name;
    }

    public List<Numbers> getNumbersList() {
        return numbersList;
    }

    public void setNumbersList(List<Numbers> numbersList) {
        this.numbersList = numbersList;
    }
    
    /**
     * Save a set of numbers.
     * @param numbers   a Numbers object
     */
    public void addNumbers(Numbers numbers) {
        this.numbersList.add(numbers);
    }
    
    /**
     * Remove the saved numbers in the given index.
     * @param i index
     */
    public void removeNumbers(int i) {
        this.numbersList.remove(i);
    }

    public double getMoneyWon() {
        return moneyWon;
    }
    
    /**
     * Add amount of money won to the user.
     * @param winnings amount of money won
     */
    public void addWin(int winnings) {
        this.moneyWon += winnings;
    }

    public double getMoneyLost() {
        return moneyLost;
    }
    
    /**
     * Add amount of money lost to user.
     * @param losses amount of money lost
     */
    public void addLoss(double losses) {
        this.moneyLost += losses;
    }
}
