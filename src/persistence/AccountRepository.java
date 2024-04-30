package persistence;

import config.DatabaseConfiguration;
import model.account.Account;
import model.account.CurrentAccount;
import model.account.DepositAccount;
import model.account.DepositType;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AccountRepository {

    public Account get(int id) {
        String selectSql = "SELECT * FROM account WHERE account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToAccount(resultSet);
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

    public Account getByIban(String iban) {
        String selectSql = "SELECT * FROM account WHERE iban = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setString(1, iban);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToAccount(resultSet);
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

    public List<Account> getUserAccounts(int userId) {
        List<Account> accounts = new ArrayList<>();
        String selectSql = "SELECT * FROM public.account WHERE user_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                while (resultSet.next()) {
                    accounts.add(mapResultSetToAccount(resultSet));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public CurrentAccount getUserCurrentAccount(int userId) {
        String selectSql = "SELECT * FROM public.current_account WHERE user_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToCurrentAccount(resultSet);
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

    public CurrentAccount getCurrentAccountByIban(String iban) {
        String selectSql = "SELECT * FROM public.current_account WHERE iban = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setString(1, iban);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToCurrentAccount(resultSet);
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

    public List<DepositAccount> getUserDepositAccounts(int userId) {
        List <DepositAccount> depositAccounts = new ArrayList<>();
        String selectSql = "SELECT da.account_id, da.balance, da.iban, da.user_id, da.last_renewal_date, dt.deposit_type_id, dt.months_duration, dt.interest_rate\n" +
                "FROM public.deposit_account as da, public.deposit_type as dt\n" +
                "WHERE da.user_id = ?\n" +
                "AND da.deposit_type_id = dt.deposit_type_id;";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                while (resultSet.next()) {
                    depositAccounts.add(mapResultSetToDepositAccount(resultSet));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return depositAccounts;
    }

    public DepositAccount getDepositAccountById(int accountId) {
        String selectSql = "SELECT da.account_id, da.balance, da.iban, da.user_id, da.last_renewal_date, dt.deposit_type_id, dt.months_duration, dt.interest_rate\n" +
                "FROM public.deposit_account as da, public.deposit_type as dt\n" +
                "WHERE da.account_id = ?\n" +
                "AND da.deposit_type_id = dt.deposit_type_id;";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(selectSql)) { //try-with-resources
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { //try-with-resources
                if (resultSet.next()) {
                    return mapResultSetToDepositAccount(resultSet);
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

    public int addCurrentAccount(CurrentAccount account) {
        String preparedSql = "{call insert_current_account(?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) {
            cstmt.setDouble(1, account.getBalance());
            cstmt.setString(2, account.getIban());
            cstmt.setInt(3, account.getUserId());
            cstmt.registerOutParameter(4, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(4);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addDepositAccount(DepositAccount account) {
        String preparedSql = "{call insert_deposit_account(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cstmt = DatabaseConfiguration.getConnection().prepareCall(preparedSql)) {
            cstmt.setDouble(1, account.getBalance());
            cstmt.setString(2, account.getIban());
            cstmt.setInt(3, account.getUserId());
            cstmt.setDate(4, new java.sql.Date(account.getLastRenewalDate().getTime()));
            cstmt.setInt(5, account.getDepositType().getDepositTypeId());
            cstmt.registerOutParameter(6, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(6);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addBalance(Account account, double amount) {
        String updateBalanceSql = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(updateBalanceSql)) { //try-with-resources
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, account.getAccountId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void subtractBalance(Account account, double amount) {
        String updateBalanceSql = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(updateBalanceSql)) { //try-with-resources
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, account.getAccountId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDepositAccount(DepositAccount depositAccount) {
        Date currentDate = Calendar.getInstance().getTime();
        Date lastRenewalDate = depositAccount.getLastRenewalDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastRenewalDate);
        calendar.add(Calendar.MONTH, depositAccount.getDepositType().getMonthsDuration());
        Date nextRenewalDate = calendar.getTime();

        if (!nextRenewalDate.after(currentDate)) {
            String updateLastRenewalDateSql = "UPDATE deposit_account SET last_renewal_date = ? WHERE account_id = ?";
            try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(updateLastRenewalDateSql)) { //try-with-resources
                preparedStatement.setDate(1, new java.sql.Date(currentDate.getTime()));
                preparedStatement.setInt(2, depositAccount.getAccountId());
                preparedStatement.executeUpdate();
                addBalance(depositAccount, depositAccount.getDepositType().getInterestRate()/100 * depositAccount.getBalance());
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Account account) {
        String deleteSql = "DELETE FROM account WHERE account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(deleteSql)) { //try-with-resources
            preparedStatement.setInt(1, account.getAccountId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getString(3), resultSet.getInt(4));
    }

    private CurrentAccount mapResultSetToCurrentAccount(ResultSet resultSet) throws SQLException {
        return new CurrentAccount(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getString(3), resultSet.getInt(4));
    }

    private DepositAccount mapResultSetToDepositAccount(ResultSet resultSet) throws SQLException {
        return new DepositAccount(resultSet.getInt(1), resultSet.getDouble(2),
                resultSet.getString(3), resultSet.getInt(4), resultSet.getDate(5),
                new DepositType(resultSet.getInt(6), resultSet.getInt(7), resultSet.getDouble(8)));
    }
}
