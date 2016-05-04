package com.luxoft.bankapp.databasemanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class ClientDAOTest {
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
}
