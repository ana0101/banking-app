package persistence;

import config.DatabaseConfiguration;
import model.card.CardHolder;

import java.sql.*;

public class CardHolderRepository {

    public int add(CardHolder cardHolder) {
        String preparedSql = "{call insert_card_holder(?, ?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) {
            cstmt.setString(1, cardHolder.getFirstName());
            cstmt.setString(2, cardHolder.getLastName());
            cstmt.setString(3, cardHolder.getCnp());
            cstmt.setInt(4, cardHolder.getCardId());
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(5);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
