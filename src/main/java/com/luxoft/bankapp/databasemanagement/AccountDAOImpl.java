package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void save(Account account) throws DAOException {
        /*String sqlUpdateAccount = "UPDATE ACCOUNTS;";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlAddAccount);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.setFloat(3, account.getBalance());
            if (account.getAccountType().equals("s")) {
                stmt.setFloat(4, Types.INTEGER);
            } else {
                stmt.setFloat(4, ((CheckingAccount) account).getOverdraft());
            }
            stmt.execute();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void add(Account account, int bankId, int clientId) throws DAOException {
        String sqlAddAccount = "INSERT INTO ACCOUNTS VALUES(?, ?, ?, ?);";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlAddAccount);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.setFloat(3, account.getBalance());
            if (account.getAccountType().equals("s")) {
                stmt.setFloat(4, Types.INTEGER);
            } else {
                stmt.setFloat(4, ((CheckingAccount) account).getOverdraft());
            }
            stmt.execute();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeByClientId(int clientId) throws DAOException {
        String sqlRemoveAccounts = "DROP COLUMN FROM TABLE ACCOUNTS AS A" +
                " WHERE A.CLIENT_ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlRemoveAccounts);
            stmt.setInt(1, clientId);
            stmt.execute();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
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
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return resultAccountList;
    }

}
