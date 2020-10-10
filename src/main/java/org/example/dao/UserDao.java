package org.example.dao;

import org.example.exceptions.ObjectNotFoundException;
import org.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final Connector CONNECTOR;

    public UserDao() {
        CONNECTOR = new Connector();
    }

    public User getUserByEmailAndPassword(String email, String password) throws ObjectNotFoundException {
        String selectStatement = "SELECT * FROM users WHERE email = ? and password = ?";

        try {
            CONNECTOR.connect();
            PreparedStatement preparedStatement = CONNECTOR.connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = getUser(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        return user.setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"));
    }
}
