package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public interface BankDAO {
    /**
            * Finds Bank by its name.
    * Do not load the list of the clients.
            * @ Param name
    * @ Return
    */
    Bank getBankByName (String name) throws DAOException, BankNotFoundException;

    void save(Bank bank) throws DAOException;

    void remove(Bank bank) throws DAOException;

}
