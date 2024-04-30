package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSetup {

    public void createInsertFunctions() throws SQLException {
        String createInsertUserFunction = "CREATE OR REPLACE FUNCTION insert_user \n" +
                "\t(IN first_name VARCHAR(50), IN last_name VARCHAR(50), IN cnp CHARACTER(13), IN username VARCHAR(50), IN password VARCHAR(50), OUT user_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO public.user (first_name, last_name, cnp, username, password)\n" +
                "\tVALUES (first_name, last_name, cnp, username, password);\n" +
                "END;\n" +
                "$$;";

        String createInsertCurrentAccountFunction = "CREATE OR REPLACE FUNCTION insert_current_account\n" +
                "\t(IN balance DOUBLE PRECISION, IN iban VARCHAR(50), IN user_id INTEGER, OUT account_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO public.current_account (balance, iban, user_id)\n" +
                "\tVALUES (balance, iban, user_id);\n" +
                "END;\n" +
                "$$;";

        String createInsertDepositAccountFunction = "CREATE OR REPLACE FUNCTION insert_deposit_account\n" +
                "\t(IN balance DOUBLE PRECISION, IN iban VARCHAR(50), IN user_id INTEGER, \n" +
                "\t IN last_renewal_date DATE, IN deposit_type_id INTEGER, OUT account_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO deposit_account (balance, iban, user_id, last_renewal_date, deposit_type_id)\n" +
                "\tVALUES (balance, iban, user_id, last_renewal_date, deposit_type_id);\n" +
                "END;\n" +
                "$$;";

        String createInsertCardFunction = "CREATE OR REPLACE FUNCTION insert_card(\n" +
                "    in_card_number CHARACTER(16),\n" +
                "    in_expiration_date DATE,\n" +
                "    in_current_account_id INTEGER\n" +
                ")\n" +
                "RETURNS INTEGER AS\n" +
                "$$\n" +
                "DECLARE\n" +
                "    v_card_id INTEGER;\n" +
                "BEGIN\n" +
                "    INSERT INTO card (card_number, expiration_date, current_account_id)\n" +
                "    VALUES (in_card_number, in_expiration_date, in_current_account_id)\n" +
                "    RETURNING card_id INTO v_card_id;\n" +
                "\n" +
                "    RETURN v_card_id;\n" +
                "END;\n" +
                "$$\n" +
                "LANGUAGE plpgsql;\n";

        String createInsertCardHolderFunction = "CREATE OR REPLACE FUNCTION insert_card_holder\n" +
                "\t(IN first_name VARCHAR(50), IN last_name VARCHAR(50), IN cnp CHARACTER(13), IN card_id INTEGER, OUT card_holder_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO card_holder (first_name, last_name, cnp, card_id)\n" +
                "\tVALUES (first_name, last_name, cnp, card_id);\n" +
                "END;\n" +
                "$$;";

        String createInsertCardTransactionFunction = "CREATE OR REPLACE FUNCTION insert_card_transaction\n" +
                "\t(IN amount DOUBLE PRECISION, IN date DATE, IN description VARCHAR(100), IN card_id INTEGER, OUT transaction_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO card_transaction (amount, date, description, card_id)\n" +
                "\tVALUES (amount, date, description, card_id);\n" +
                "END;\n" +
                "$$;";

        String createInsertTransferFunction = "CREATE OR REPLACE FUNCTION insert_transfer\n" +
                "\t(IN amount DOUBLE PRECISION, IN date DATE, IN description VARCHAR(100), \n" +
                "\t IN payer_account_id INTEGER, IN recipient_account_id INTEGER, OUT transfer_id INTEGER)\n" +
                "\tRETURNS INTEGER\n" +
                "\tLANGUAGE plpgsql\n" +
                "\tas \n" +
                "$$\n" +
                "BEGIN\n" +
                "\tINSERT INTO transfer (amount, date, description, payer_account_id, recipient_account_id)\n" +
                "\tVALUES (amount, date, description, payer_account_id, recipient_account_id);\n" +
                "END;\n" +
                "$$;";

        Connection databaseConnection = DatabaseConfiguration.getConnection();
        Statement stmt = databaseConnection.createStatement();

        stmt.execute(createInsertUserFunction);
        stmt.execute(createInsertCurrentAccountFunction);
        stmt.execute(createInsertDepositAccountFunction);
        stmt.execute(createInsertCardFunction);
        stmt.execute(createInsertCardHolderFunction);
        stmt.execute(createInsertCardTransactionFunction);
        stmt.execute(createInsertTransferFunction);
    }
}
