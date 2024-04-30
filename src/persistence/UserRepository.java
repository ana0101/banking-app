package persistence;

import config.DatabaseConfiguration;
import model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public User getByUsername(String username) {
        String selectSql = "SELECT * FROM public.user WHERE username = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String selectSql = "SELECT * FROM public.user";
        try (Statement stmt = DatabaseConfiguration.getConnection().createStatement();
             ResultSet resultSet = stmt.executeQuery(selectSql)) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int add(User user) {
        String preparedSql = "{call insert_user(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) { //try-with-resources
            cstmt.setString(1, user.getFirstName());
            cstmt.setString(2, user.getLastName());
            cstmt.setString(3, user.getCnp());
            cstmt.setString(4, user.getUsername());
            cstmt.setString(5, user.getPassword());
            cstmt.registerOutParameter(6, Types.INTEGER); //out param (result of the function call)
            cstmt.execute();
            return cstmt.getInt(6);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
    }
}
