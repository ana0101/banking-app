package persistence;

import config.DatabaseConfiguration;
import model.card.CardTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardTransactionRepository {

    public List<CardTransaction> getCardTransactionsByCardId(int cardId) {
        List <CardTransaction> cardTransactions = new ArrayList<>();
        String selectSql = "SELECT * FROM card_transaction WHERE card_id = ?;";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setInt(1, cardId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                while (resultSet.next()) {
                    cardTransactions.add(mapResultSetToCardTransaction(resultSet));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cardTransactions;
    }

    public int add(CardTransaction cardTransaction) {
        String preparedSql = "{call insert_card_transaction(?, ?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) {
            cstmt.setDouble(1, cardTransaction.getAmount());
            cstmt.setDate(2, new java.sql.Date(cardTransaction.getDate().getTime()));
            cstmt.setString(3, cardTransaction.getDescription());
            cstmt.setInt(4, cardTransaction.getCardId());
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(5);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private CardTransaction mapResultSetToCardTransaction(ResultSet resultSet) throws SQLException {
        return new CardTransaction(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getDate(3),
                resultSet.getString(4), resultSet.getInt(5));
    }
}
