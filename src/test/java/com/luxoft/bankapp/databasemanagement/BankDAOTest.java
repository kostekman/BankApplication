package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class BankDAOTest {
    static DatabaseManager databaseManager;

    @BeforeClass
    public static void initialize(){
        databaseManager = new DatabaseManager();


    }

    @Before
    public void createDatabase(){
        databaseManager.createDatabase();
    }
    @Test
    public void addingBankTest() throws DAOException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);
        assertEquals("ID doesn't match", 1, bank.getId());
    }

    @Test
    public void gettingBankByName() throws DAOException, BankNotFoundException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);
        Bank otherBank = bankDAO.getBankByName("test");
        assertEquals("Banks not the same", bank, otherBank);
    }

    @Test(expected = BankNotFoundException.class)
    public void removingBankTest() throws DAOException, BankNotFoundException {
        BankDAO bankDAO = new BankDAOImpl();
        Bank bank = new Bank("test");
        bankDAO.save(bank);
        bankDAO.remove(bank);
        bankDAO.getBankByName("test");
    }

    @After
    public void deleteDatabase(){
        databaseManager.dropDatabase();
    }
}
