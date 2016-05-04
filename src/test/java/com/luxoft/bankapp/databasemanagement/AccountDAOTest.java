package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class AccountDAOTest {
    static DatabaseManager databaseManager;

    @BeforeClass
    public static void initialize(){
        databaseManager = new DatabaseManager();
    }

    @Before
    public void createDatabase(){
        databaseManager.createDatabase();
    }

    @After
    public void deleteDatabase(){
        databaseManager.dropDatabase();
    }

    @Test
    public void addAccountsTest() throws DAOException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);

        AccountDAO accountDAO = new AccountDAOImpl();
        Account account1 = new SavingAccount(100);
        Account account2 = new CheckingAccount(100, 100);
        client.addAccount(account1);
        client.addAccount(account2);
        accountDAO.add(account1, client.getId(), bank.getId());
        accountDAO.add(account2, client.getId(), bank.getId());
        ArrayList<Account> accountsList = new ArrayList<>(accountDAO.getClientAccounts(1));
        assertTrue(accountsList.contains(account1) && accountsList.contains(account2));
    }

    @Test
    public void removeAccountsTest() throws DAOException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);

        AccountDAO accountDAO = new AccountDAOImpl();
        Account account1 = new SavingAccount(100);
        Account account2 = new CheckingAccount(100, 100);
        client.addAccount(account1);
        client.addAccount(account2);
        accountDAO.add(account1, client.getId(), bank.getId());
        accountDAO.add(account2, client.getId(), bank.getId());
        accountDAO.removeClientAccounts(1, 1);
        ArrayList<Account> accountsList = new ArrayList<>(accountDAO.getClientAccounts(1));
        assertFalse(accountsList.contains(account1) && accountsList.contains(account2));
    }

    @Test
    public void updateAccountsTest() throws DAOException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client("Testy McTestface", "Test", Gender.MALE, "test@test.test", "737737737", 1f);
        clientDAO.save(client, 1);

        AccountDAO accountDAO = new AccountDAOImpl();
        Account account1 = new SavingAccount(100);
        client.addAccount(account1);
        accountDAO.add(account1, client.getId(), bank.getId());
        account1.deposit(300);
        accountDAO.update(account1);
        ArrayList<Account> accountsList = new ArrayList<>(accountDAO.getClientAccounts(1));
        assertTrue(account1.getBalance() == accountsList.get(0).getBalance());
    }
}
