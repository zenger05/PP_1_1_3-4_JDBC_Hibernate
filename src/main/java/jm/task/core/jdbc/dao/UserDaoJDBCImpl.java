package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS user(
            id BIGINT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(100) NOT NULL,
            lastName VARCHAR(100) NOT NULL,
            age TINYINT NOT NULL
            )
            """;

    private final String sqlDropUserTable = """
            DROP TABLE IF EXISTS new.user
            """;

    private final String sqlSaveUser = """
            INSERT INTO user(name, lastName, age) VALUES 
            (?, ?, ?)
            """;

    private final String sqlDeleteById = """
            DELETE FROM new.user WHERE id = ?
            """;

    private final String sqlGetAllUser = """
            SELECT id,
                   name,
                   lastName,
                   age  
            FROM new.user
            """;

    private final String sqlDeleteAll = """
            TRUNCATE TABLE user 
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();
        ) {
            statement.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();
        ) {
            statement.execute(sqlDropUserTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sqlSaveUser);
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteById);
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAllUser)
        ) {
            List<User> listUser = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                listUser.add(user);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteAll)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
