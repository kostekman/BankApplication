package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class ClientDAOTest {
    static DatabaseManager databaseManager;

    @BeforeClass
    public static void initialize() {
        databaseManager = new DatabaseManager();


    }

    @Before
    public void createDatabase() {
        databaseManager.createDatabase();
    }

    @After
    public void deleteDatabase() {
        databaseManager.dropDatabase();
    }

    @Test
    public void addClient() throws DAOException, TooManyClientsFoundException, ClientNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);
        Client newClient = clientDAO.findClientByName("Testy McTestface");
        assertTrue("no clients found", TestService.isEquals(client, newClient));
    }

    @Test(expected = ClientNotFoundException.class)
    public void removeClient() throws DAOException, TooManyClientsFoundException, ClientNotFoundException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);
        clientDAO.remove(client.getId(), 1);
        clientDAO.findClientByName("Testy McTestface");
    }

    @Test(expected = ClientNotFoundException.class)
    public void addMultipleBanksAndClientsAndRemoveThem() throws DAOException, TooManyClientsFoundException, ClientNotFoundException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);
        Bank bank2 = new Bank("test2");
        bankDAO.save(bank2);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);
        clientDAO.save(client, 2);
        clientDAO.remove(client.getId(), 1);
        assertEquals("client removed too early", client, clientDAO.findClientByName("Testy McTestface"));
        clientDAO.remove(client.getId(), 2);
        clientDAO.findClientByName("Testy McTestface");
    }

    @Test
    public void getAllClientsTest() throws DAOException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);

        Client client2 = new Client("That Othertestguy", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client2, 1);

        ArrayList<Client> clientsList = new ArrayList<>(clientDAO.getAllClients(1));
        assertTrue(clientsList.contains(client) && clientsList.contains(client2));
    }

}
