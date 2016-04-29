package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.model.Client;

import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public interface ClientDAO {
    /**
            * Return client by its name, initialize client accounts.
    * @param bankId
    * @param name
    * @return
            */
    Client findClientByName (int bankId, String name) throws DAOException, TooManyClientsFoundException, ClientNotFoundException;

    /**
            * Returns the list of all clients of the Bank
    * And their accounts
    * @param bankId
    * @return
            */
    List<Client> getAllClients (int bankId) throws DAOException;

    /**
            * Method should insert new Client (if id == null)
    * Or update client in database
    * @param client
    */
    void save (Client client, int bankId) throws DAOException;

    /**
            * Method removes client from Database
    * @param clientId
    */
    void remove (int clientId, int bankId) throws DAOException;
}
