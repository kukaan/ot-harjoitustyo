package lottokone.dao;

import java.util.List;
import lottokone.domain.Numbers;

public interface NumbersDao {
    
    /**
     * Create a new object for playable numbers.
     * @param userId    index of User in database
     * @param numbers   numbers will be stored in a String for now
     * @return operationSuccessful
     */
    boolean createNumbers(int userId, String numbers);
    
    /**
     * Find the numbers of a User.
     * @param userId    index of User in database
     * @return  list of all Numbers objects connected to a User
     */
    List<Numbers> findByUserId(int userId);
    
}
