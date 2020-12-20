package lottokone.dao;

import java.io.*;
import java.util.ArrayList;
import lottokone.domain.User;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class SQLiteUserDaoTest {
    
    private UserDao userDao;
    private final File dbFile = new File("test.db");
    
    @Before
    public void setUp() throws IOException {
        dbFile.createNewFile();
        userDao = new SQLiteUserDao("test.db");
        userDao.createUser("asdf");
        userDao.createUser("qwerty");
    }
    
    @After
    public void tearDown() {
        dbFile.delete();
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
