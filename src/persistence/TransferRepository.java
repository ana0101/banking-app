package persistence;

import config.DatabaseConfiguration;
import model.transfer.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferRepository {

    public List<Transfer> getTransfersByAccountId (int accountId) {
        List <Transfer> transfers = new ArrayList<>();
        String selectSql = "SELECT * FROM transfer WHERE payer_account_id = ? OR recipient_account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                while (resultSet.next()) {
                    transfers.add(mapResultSetToTransfer(resultSet));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transfers;
    }

    public int add(Transfer transfer) {
        String preparedSql = "{call insert_transfer(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) {
            cstmt.setDouble(1, transfer.getAmount());
            cstmt.setDate(2, new java.sql.Date(transfer.getDate().getTime()));
            cstmt.setString(3, transfer.getDescription());
            cstmt.setInt(4, transfer.getPayerAccountId());
            cstmt.setInt(5, transfer.getRecipientAccountId());
            cstmt.registerOutParameter(6, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(6);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Transfer mapResultSetToTransfer(ResultSet resultSet) throws SQLException {
        return new Transfer(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getDate(3),
                resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));
    }
}
