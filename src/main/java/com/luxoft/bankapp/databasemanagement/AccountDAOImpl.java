package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.loggers.CurrentDateAndTime;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void update(Account account) throws DAOException {
        String sqlUpdateAccount = "UPDATE ACCOUNTS SET BALANCE = ?, OVERDRAFT = ? WHERE ID = ?;";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlUpdateAccount);
            stmt.setFloat(1, account.getBalance());
            if (account instanceof CheckingAccount) {
                stmt.setFloat(2, ((CheckingAccount) account).getOverdraft());
            } else {
                stmt.setNull(2, Types.FLOAT);
            }
            stmt.setInt(3, account.getId());
            stmt.execute();
            closeConnection();
            BankAppLogger.log(Level.INFO, "DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | Account id" + account.getId() + "updated");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

    @Override
    public synchronized void add(Account account, int bankId, int clientId) throws DAOException {
        String sqlAddAccount = "INSERT INTO ACCOUNTS(BANK_ID, CLIENT_ID, BALANCE, OVERDRAFT) VALUES(?, ?, ?, ?);";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlAddAccount);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.setFloat(3, account.getBalance());
            if (account.getAccountType().equals("s")) {
                stmt.setNull(4, Types.FLOAT);
            } else {
                stmt.setFloat(4, ((CheckingAccount) account).getOverdraft());
            }
            stmt.execute();
            closeConnection();
            setAccountId(account, bankId, clientId);
            BankAppLogger.log(Level.INFO, "DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | Account id" + account.getId() + "created");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

    private void setAccountId(Account account, int bankId, int clientId) throws DAOException {
        if (account instanceof CheckingAccount) {
            setCheckingAccountId(account, bankId, clientId);
        } else if (account instanceof SavingAccount) {
            setSavingAccountId(account, bankId, clientId);
        }
    }

    private void setSavingAccountId(Account account, int bankId, int clientId) throws DAOException {
        String sqlSelectNull = "SELECT A.ID FROM ACCOUNTS AS A" +
                " WHERE A.CLIENT_ID = ? AND A.BANK_ID = ? AND A.OVERDRAFT IS NULL";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlSelectNull);
            stmt.setInt(1, clientId);
            stmt.setInt(2, bankId);
            ResultSet accounts = stmt.executeQuery();
            while (accounts.next()) {
                account.setId(accounts.getInt(1));
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }

    private void setCheckingAccountId(Account account, int bankId, int clientId) throws DAOException {
        String sqlSelectNotNull = "SELECT A.ID FROM ACCOUNTS AS A" +
                " WHERE A.CLIENT_ID = ? AND A.BANK_ID = ? AND A.OVERDRAFT IS NOT NULL";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlSelectNotNull);
            stmt.setInt(1, clientId);
            stmt.setInt(2, bankId);
            ResultSet accounts = stmt.executeQuery();
            while (accounts.next()) {
                account.setId(accounts.getInt(1));
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void removeClientAccounts(int bankId, int clientId) throws DAOException {
        String sqlRemoveClient = "DELETE FROM ACCOUNTS WHERE CLIENT_ID = ? AND BANK_ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlRemoveClient);
            stmt.setInt(1, clientId);
            stmt.setInt(2, bankId);
            stmt.execute();
            closeConnection();
            BankAppLogger.log(Level.INFO, "DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | Accounts for client_id: " + clientId + "and bankId: " + bankId + "removed");
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> getClientAccounts(int clientId) throws DAOException {
        List<Account> resultAccountList = new LinkedList<>();
        resultAccountList.addAll(getCheckingAccounts(clientId));
        resultAccountList.addAll(getSavingAccounts(clientId));

        return resultAccountList;
    }

    private List<Account> getCheckingAccounts(int clientId) throws DAOException {
        String sqlSelectNotNull = "SELECT A.ID, A.BALANCE, A.OVERDRAFT FROM ACCOUNTS AS A" +
                " WHERE A.CLIENT_ID = ? AND A.OVERDRAFT IS NOT NULL";
        PreparedStatement stmt;
        List<Account> resultAccountList = new LinkedList<>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlSelectNotNull);
            stmt.setInt(1, clientId);
            ResultSet accounts = stmt.executeQuery();
            while (accounts.next()) {
                Account newAccount = new CheckingAccount(accounts.getFloat(2), accounts.getFloat(3));
                newAccount.setId(accounts.getInt(1));
                resultAccountList.add(newAccount);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return resultAccountList;
    }

    private List<Account> getSavingAccounts(int clientId) throws DAOException {
        String sqlSelectNotNull = "SELECT A.ID, A.BALANCE FROM ACCOUNTS AS A" +
                " WHERE A.CLIENT_ID = ? AND A.OVERDRAFT IS NULL";
        PreparedStatement stmt;
        List<Account> resultAccountList = new LinkedList<>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlSelectNotNull);
            stmt.setInt(1, clientId);
            ResultSet accounts = stmt.executeQuery();
            while (accounts.next()) {
                Account newAccount = new SavingAccount(accounts.getFloat(2));
                newAccount.setId(accounts.getInt(1));
                resultAccountList.add(newAccount);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return resultAccountList;
    }

}
