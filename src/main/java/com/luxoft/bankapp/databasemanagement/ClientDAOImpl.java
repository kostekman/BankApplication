package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {
    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {
        // TODO
        return null;
    }

    @Override
    public void save(Client client) throws DAOException {
        // TODO
    }

    @Override
    public void remove(Client client) throws DAOException {
        // TODO
    }
}
