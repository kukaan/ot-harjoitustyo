package lottokone.dao;

import java.util.List;
import lottokone.domain.Numbers;

public interface NumbersDao {
    
    boolean createNumbers(int userId, String numbers);
    
    List<Numbers> findByUserId(int userId);
    
}
