package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public interface AccountDAO {
    public void update(Account account) throws DAOException;
    public void add(Account account, int BankId, int ClientId) throws DAOException;
    public void removeByClientId(int idClient) throws DAOException;
    public List<Account> getClientAccounts(int idClient) throws DAOException;
}
