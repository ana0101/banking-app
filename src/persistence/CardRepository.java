package persistence;

import config.DatabaseConfiguration;
import model.card.Card;
import model.card.CardHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public Card get(int id) {
        String selectSql = "SELECT c.card_id, c.card_number, c.expiration_date, c.current_account_id, \n" +
                "\tch.card_holder_id, ch.first_name, ch.last_name, ch.cnp\n" +
                "FROM card as c, card_holder as ch\n" +
                "WHERE c.card_id = ?\n" +
                "AND c.card_id = ch.card_id;";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToCard(resultSet);
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

    public List<Card> getCardsByCurrentAccountId(int currentAccountId) {
        List <Card> cards = new ArrayList<>();
        String selectSql = "SELECT c.card_id, c.card_number, c.expiration_date, c.current_account_id, \n" +
                "\tch.card_holder_id, ch.first_name, ch.last_name, ch.cnp\n" +
                "FROM card as c, card_holder as ch\n" +
                "WHERE c.current_account_id = ?\n" +
                "AND c.card_id = ch.card_id;";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, currentAccountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                while (resultSet.next()) {
                    cards.add(mapResultSetToCard(resultSet));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public int add(Card card) {
        String sql = "{? = call insert_card(?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(sql)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setString(2, card.getCardNumber());
            cstmt.setDate(3, new java.sql.Date(card.getExpirationDate().getTime()));
            cstmt.setInt(4, card.getCurrentAccountId());
            cstmt.execute();
            int generatedCardId = cstmt.getInt(1);
            return generatedCardId;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void delete(Card card) {
        String deleteSql = "DELETE FROM card WHERE card_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, card.getCardId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Card mapResultSetToCard(ResultSet resultSet) throws SQLException {
        return new Card(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getInt(4),
                new CardHolder(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getInt(1)));
    }
}
