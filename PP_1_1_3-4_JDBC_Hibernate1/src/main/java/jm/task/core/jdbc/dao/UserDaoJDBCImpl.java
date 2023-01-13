package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY not NULL auto_increment, name VARCHAR(20) not NULL, lastName VARCHAR(20) not NULL, age INT not NULL)");
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try(Connection conn = Util.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try(Connection conn = Util.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            conn.commit();
        } catch (SQLException e) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
