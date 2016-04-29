package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void save(Account account) throws DAOException {
        // TODO
    }

    @Override
    public void add(Account account) throws DAOException {
        // TODO
    }

    @Override
    public void removeByClientId(int idClient) throws DAOException {
        // TODO
    }

    @Override
    public List<Account> getClientAccounts(int idClient) throws DAOException {
        // TODO
        return null;
    }
}
