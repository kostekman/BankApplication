package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class DatabaseManager extends BaseDAOImpl {
    public void createDatabase(){
        String sql = "CREATE TABLE BANKS ( \n" +
                "ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                "NAME VARCHAR(255)\n" +
                ");\n" +
                "CREATE TABLE CLIENTS (\n" +
                "ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                "NAME VARCHAR(255),\n" +
                "CITY VARCHAR(255),\n" +
                "GENDER BOOLEAN,\n" +
                "PHONENUMBER VARCHAR(255),\n" +
                "EMAIL VARCHAR(255),\n" +
                "INITIALOVERDRAFT FLOAT\n" +
                ");\n" +
                "CREATE TABLE BANK_CLIENTS(\n" +
                "BANK_ID INT,\n" +
                "CLIENT_ID INT,\n" +
                "FOREIGN KEY(BANK_ID) REFERENCES BANKS(ID),\n" +
                "FOREIGN KEY(CLIENT_ID) REFERENCES CLIENTS(ID)\n" +
                ");\n" +
                "CREATE TABLE ACCOUNTS (\n" +
                "ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                "BANK_ID INT,\n" +
                "CLIENT_ID INT,\n" +
                "BALANCE FLOAT,\n" +
                "OVERDRAFT FLOAT,\n" +
                "FOREIGN KEY(BANK_ID) REFERENCES BANKS(ID),\n" +
                "FOREIGN KEY(CLIENT_ID) REFERENCES CLIENTS(ID)\n" +
                ");";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.execute();
            closeConnection();
        }catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropDatabase(){
        String sql = "DROP TABLE ACCOUNTS, BANK_CLIENTS, CLIENTS, BANKS";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.execute();
            closeConnection();
        }catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
