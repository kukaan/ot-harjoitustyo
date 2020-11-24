package lottokone.dao;

import java.util.ArrayList;
import java.util.List;
import lottokone.domain.User;

public class TemporaryUserDao implements UserDao {
    private List<User> users;

    public TemporaryUserDao() {
        this.users = new ArrayList<>();
    }

    @Override
    public User create(User user) {
        if (findByName(user.getName()) != null) {
            return null;
        }
        users.add(user);
        return findByName(user.getName());
    }

    @Override
    public User findByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }
    
}
