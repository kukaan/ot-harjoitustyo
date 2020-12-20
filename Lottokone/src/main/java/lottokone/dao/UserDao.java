package lottokone.dao;

import java.util.List;
import lottokone.domain.Numbers;
import lottokone.domain.User;

public interface UserDao {
    
    /**
     * Save a new User.
     * @param name  username
     * @return  operation completed successfully
     */
    boolean createUser(String name);
    
    /**
     * Find a User by its name.
     * @param name  the name of the User being searched for
     * @return the User found or null if no such name is found
     */
    User findByName(String name);
    
    /**
     * Add numbers to a User.
     * @param id    user id
     * @param numbers   playable numbers
     * @return  operation completed successfully
     */
    boolean addNumbers(int id, Numbers numbers);
    
    /**
     * Add a sum of money to the wins of User.
     * @param id    user id
     * @param moneySum  amount of money won
     */
    public void addWin(int id, int moneySum);
    
    /**
     * Add a sum of money to the losses of User.
     * @param id    user id
     * @param moneySum  amount of money lost
     */
    public void addLoss(int id, int moneySum);
    
}
