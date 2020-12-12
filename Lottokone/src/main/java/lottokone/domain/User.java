package lottokone.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private List<Numbers> numbersList;
    private double winningsSum;
    private double lossesSum;
    
    /**
     * A user account.
     * @param name  just a name
     */
    public User(String name) {
        this(name, new ArrayList<>());
    }
    
    /**
     * A user account.
     * @param name  just a name
     * @param numbersList   a List for the numbers of this User to be saved in
     */
    public User(String name, List<Numbers> numbersList) {
        this.name = name;
        this.numbersList = numbersList;
        this.winningsSum = 0;
        this.lossesSum = 0;
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

    public double getWinningsSum() {
        return winningsSum;
    }
    
    /**
     * Add amount of money won to the user.
     * @param winnings amount of money won
     */
    public void addWin(double winnings) {
        this.winningsSum += winnings;
    }

    public double getLossesSum() {
        return lossesSum;
    }
    
    /**
     * Add amount of money lost to user.
     * @param losses amount of money lost
     */
    public void addLoss(double losses) {
        this.lossesSum += losses;
    }
}
