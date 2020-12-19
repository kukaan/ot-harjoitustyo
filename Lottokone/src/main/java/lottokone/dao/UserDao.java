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
     * Get all Users.
     * @return a List of all saved Users
     */
    List<User> findAll();
    
    boolean addNumbers(int id, Numbers numbers);
    
    public void addWin(int id, int moneySum);
    
    public void addLoss(int id, int moneySum);
    
}
