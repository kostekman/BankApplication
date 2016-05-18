package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.Connection;

/**
 * Created by AKoscinski on 2016-04-29.
 */
interface BaseDAO {
    Connection openConnection() throws DAOException;
    void closeConnection();
}
