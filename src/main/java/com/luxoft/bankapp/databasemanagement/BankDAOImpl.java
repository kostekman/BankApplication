package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.loggers.CurrentDateAndTime;
import com.luxoft.bankapp.model.Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

    public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
        Bank bank = new Bank(name);
        String sql = "SELECT ID, NAME FROM BANKS WHERE name=?";
        PreparedStatement stmt;
        try {
            conn = openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                bank.setId(id);
            } else {
                throw new BankNotFoundException(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return bank;
    }

    @Override
    public void save(Bank bank) throws DAOException {
        String sqlInsert = "INSERT INTO BANKS(NAME) VALUES(?);";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, bank.getName());
            stmt.execute();
            Bank newBank = getBankByName(bank.getName());
            bank.setId(newBank.getId());
            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Bank with id: " + newBank.getId() + "saved");

        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        }
    }

    @Override
    public void remove(Bank bank) throws DAOException {
        String sql = "DELETE FROM BANKS WHERE ID = ?";
        PreparedStatement stmt;
        try {
            removeBankAccounts(bank.getId());
            removeBankClients(bank.getId());
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bank.getId());
            if (stmt.execute()) {
                throw new BankNotFoundException(bank.getName());
            }
            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Bank with id: " + bank.getId() + "removed");
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        }
    }

    private void removeBankAccounts(int id) throws DAOException {
        String sql = "DELETE FROM ACCOUNTS WHERE BANK_ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Accounts for bank with with id: " + id + "removed");
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        }
    }

    private void removeBankClients(int id) throws DAOException {
        String sql = "DELETE FROM BANK_CLIENTS WHERE BANK_ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Clients for bank with with id: " + id + "removed");
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        }
    }
}
