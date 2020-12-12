package lottokone.dao;

import java.util.ArrayList;
import lottokone.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TemporaryUserDaoTest {
    UserDao userDao;
    
    @Before
    public void setUp() {
        userDao = new TemporaryUserDao();
        userDao.createUser(new User("asdf"));
        userDao.createUser(new User("qwerty"));
    }
    
    @Test
    public void createReturnsCreatedUser() {
        User u = userDao.createUser(new User("cool"));
        assertThat(u.getName(), is(equalTo("cool")));
    }
    
    @Test
    public void createReturnsNullIfUsernameExists() {
        User u = userDao.createUser(new User("asdf"));
        assertThat(u, is(equalTo(null)));
    }
    
    @Test
    public void findByNameReturnsExistingUser() {
        User expected = new User("asdf");
        assertThat(userDao.findByName("asdf").getName(), is(equalTo(expected.getName())));
    }
    
    @Test
    public void findByNameReturnsNullIfUserNotFound() {
        assertThat(userDao.findByName("aaaa"), is(equalTo(null)));
    }
    
    @Test
    public void findAllReturnsAll() {
        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("asdf"));
        expected.add(new User("qwerty"));
        int i = 0;
        for (User u : userDao.findAll()) {
            assertThat(u.getName(), is(equalTo(expected.get(i++).getName())));
        }
    }
}
