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
public class BankDAOImpl extends BaseDAOImpl implements BankDAO{

    public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
        Bank bank = new Bank(name);
        String sql = "SELECT ID, NAME FROM BANK WHERE name=?";
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
        String sqlInsert = "INSERT INTO BANK VALUES(?);";
        PreparedStatement stmt;
        try{
            openConnection();
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, bank.getName());
            stmt.execute();
            getBankByName(bank.getName());
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally{
            closeConnection();
        }
    }

    @Override
    public void remove(Bank bank) throws DAOException {
        String sql = "DROP COLUMN FROM TABLE BANKS WHERE ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bank.getId());
            if(!stmt.execute()){
                throw new BankNotFoundException(bank.getName());
            }
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }
}
