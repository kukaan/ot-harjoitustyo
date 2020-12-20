package lottokone.dao;

import java.util.ArrayList;
import java.util.List;
import lottokone.domain.Numbers;
import lottokone.domain.User;

public class TemporaryUserDao implements UserDao {
    private List<User> users;

    /**
     * Creates a temporary storage for User objects.
     */
    public TemporaryUserDao() {
        this.users = new ArrayList<>();
    }

    @Override
    public boolean createUser(String name) {
        if (findByName(name) != null) {
            return false;
        }
        users.add(new User(users.size(), name, new ArrayList<>(), 0, 0));
        return true;
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
    
    private User findById(int id) {
        if (users.size() < id) {
            return null;
        }
        return users.get(id);
    }

    @Override
    public boolean addNumbers(int id, Numbers numbers) {
        User u = findById(id);
        if (u == null) {
            return false;
        }
        u.addNumbers(numbers);
        return true;
    }

    @Override
    public void addWin(int id, int moneySum) {
        User u = findById(id);
        if (u == null) {
            return;
        }
        u.addWin(moneySum);
    }

    @Override
    public void addLoss(int id, int moneySum) {
        User u = findById(id);
        if (u == null) {
            return;
        }
        u.addLoss(moneySum);
    }
    
}
