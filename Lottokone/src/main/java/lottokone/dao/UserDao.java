package lottokone.dao;

import java.util.List;
import lottokone.domain.User;

public interface UserDao {
    User create(User user);
    
    User findByName(String name);
    
    List<User> findAll();
}
