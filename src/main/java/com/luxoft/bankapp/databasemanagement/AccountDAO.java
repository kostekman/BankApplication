package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public interface AccountDAO {
    void update(Account account) throws DAOException;

    void add(Account account, int BankId, int ClientId) throws DAOException;

    void removeClientAccounts(int bankId, int clientId) throws DAOException;

    List<Account> getClientAccounts(int idClient) throws DAOException;
}
