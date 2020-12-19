package lottokone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lottokone.domain.LottokoneService;
import lottokone.domain.Numbers;
import lottokone.domain.User;

public class SQLiteUserDao implements UserDao {

    private Connection connection;
    private LottokoneService service;

    public SQLiteUserDao(String fileName) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            Statement statement = this.connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", name TEXT UNIQUE, moneyWon INTEGER DEFAULT 0, moneyLost INTEGER DEFAULT 0);");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Tickets (id INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", userId INTEGER, numbers STRING);");
        } catch (SQLException e) {
            printGeneralErrorMessage();
        }
    }
    
    private void printGeneralErrorMessage() {
        System.out.println("A database error occured!");
    }

    @Override
    public boolean createUser(String name) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Users (name) VALUES (?);");
            statement.setString(1, name);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            printGeneralErrorMessage();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addNumbers(int userId, Numbers numbers) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Numbers (userId, numbers) VALUES (?,?);");
            statement.setInt(1, userId);
            String s = numbers.toString();
            statement.setString(2, s.substring(1, s.length()-1));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            printGeneralErrorMessage();
            return false;
        }
        return true;
    }
    
    @Override
    public User findByName(String name) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Users WHERE name=?");
            ResultSet result = statement.executeQuery();
            statement.close();
            List<String> usersNumbers = new ArrayList<>();
            if (result.next()) {
                return new User(result.getInt("id"), result.getString(name),
                        findNumbersByUserId(result.getInt("id")),
                        result.getInt("moneyWon"), result.getInt("moneyLost"));
            }
        } catch (SQLException e) {
            printGeneralErrorMessage();
        }
        return null;
    }
    
    private List<Numbers> findNumbersByUserId(int userId) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT numbers FROM Numbers WHERE userId=?");
            ResultSet result = statement.executeQuery();
            List<String> strings = new ArrayList<>();
            while (result.next()) {
                strings.add(result.getString("numbers"));
            }
            statement.close();
            List<Numbers> numbersList = new ArrayList<>();
            for (String s : strings) {
                numbersList.add(new Numbers(service.stringToListOfNumbers(s)));
            }
            return numbersList;

        } catch (Exception e) {
            printGeneralErrorMessage();
        }
        return null;
    }

    // todo: remove from dao interface
    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Will not be implemented.");
    }

    @Override
    public void addWin(int id, int win) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("UPADTE Users SET moneyWon=moneyWon+? WHERE id=?;");
            statement.setInt(1, win);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            printGeneralErrorMessage();
        }
    }

    @Override
    public void addLoss(int id, int loss) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("UPADTE Users SET moneyLost=moneyLost+? WHERE id=?;");
            statement.setInt(1, loss);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            printGeneralErrorMessage();
        }
    }

}

