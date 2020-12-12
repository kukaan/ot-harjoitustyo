package lottokone.dao;

import java.util.List;
import lottokone.domain.User;

public interface UserDao {
    
    /**
     * Save a new User.
     * @param user  a User
     * @return  the User saved in the database or null if User already saved
     */
    User createUser(User user);
    
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
}
