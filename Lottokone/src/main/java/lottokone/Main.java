package lottokone;

import java.util.Random;
import java.util.Scanner;
import lottokone.dao.SQLiteUserDao;
import lottokone.dao.TemporaryUserDao;
import lottokone.dao.UserDao;
import lottokone.domain.LottokoneService;
import lottokone.ui.LottokoneUI;

public class Main {
    
    /**
     * Starts the program.
     * @param args 
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Random random = new Random();
//        UserDao userDao = new TemporaryUserDao();
        UserDao userDao = new SQLiteUserDao("production.db");
        LottokoneService service = new LottokoneService(random, userDao);
        
        LottokoneUI ui = new LottokoneUI(reader, service);
        ui.start();
    }
}
