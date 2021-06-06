package main.java.database;

import main.java.config.Databaseconfig;
import main.java.models.User;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

    public static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(Databaseconfig.CONNECTION_URL, Databaseconfig.USERNAME, Databaseconfig.PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<User> selectAllUsers() {
        try {
            var users = new ArrayList<User>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                User user = new User(resultSet.getString("username"), resultSet.getString("password"));
                users.add(user);
            }

            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ArrayList<>();

        }

    }

    public static void insertIntoUser(User user) {

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES(?, ?)");
            statement.setString(1, user.username);
            statement.setString(2, user.password);

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static User getUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
