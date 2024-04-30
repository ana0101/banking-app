package persistence;

import config.DatabaseConfiguration;
import model.account.DepositType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepositTypeRepository {

    public DepositType get(int id) {
        String selectSql = "SELECT * FROM deposit_type WHERE deposit_type_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToDepositType(resultSet);
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

    public List<DepositType> getAll() {
        List<DepositType> depositTypes = new ArrayList<>();
        String selectSql = "SELECT * FROM deposit_type";
        try (Statement stmt = DatabaseConfiguration.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql)) {
            while (resultSet.next()) {
                depositTypes.add(mapResultSetToDepositType(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return depositTypes;
    }

    private DepositType mapResultSetToDepositType(ResultSet resultSet) throws SQLException {
        return new DepositType(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDouble(3));
    }
}
