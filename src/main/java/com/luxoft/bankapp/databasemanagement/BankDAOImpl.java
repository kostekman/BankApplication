package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void remove(Bank bank) throws DAOException {
        String sql = "DELETE FROM BANKS WHERE ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bank.getId());
            if (stmt.execute()) {
                throw new BankNotFoundException(bank.getName());
            }
            closeConnection();
            removeBankAccounts(bank.getId());
            removeBankClients(bank.getId());
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }
}
