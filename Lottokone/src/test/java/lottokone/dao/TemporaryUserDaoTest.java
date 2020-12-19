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
        userDao.createUser("asdf");
        userDao.createUser("qwerty");
    }
    
    @Test
    public void createReturnsTrueIfUsernameUnique() {
        assertTrue(userDao.createUser("aaaa"));
    }
    
    @Test
    public void createReturnsFalseIfUsernameExists() {
        assertFalse(userDao.createUser("asdf"));
    }
    
    @Test
    public void findByNameReturnsExistingUser() {
        String expected = "asdf";
        assertThat(userDao.findByName("asdf").getName(), is(equalTo("asdf")));
    }
    
    @Test
    public void findByNameReturnsNullIfUserNotFound() {
        assertThat(userDao.findByName("aaaa"), is(equalTo(null)));
    }
    
    @Test
    public void findAllReturnsAll() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("asdf");
        expected.add("qwerty");
        int i = 0;
        for (User u : userDao.findAll()) {
            assertThat(u.getName(), is(equalTo(expected.get(i++))));
        }
    }
}
