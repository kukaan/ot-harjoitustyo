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
}
