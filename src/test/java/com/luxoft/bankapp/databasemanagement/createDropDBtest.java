package com.luxoft.bankapp.databasemanagement;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by AKoscinski on 2016-05-04.
 */
public class createDropDBtest {

    static DatabaseManager databaseManager;
    @BeforeClass
    public static void initialize(){
        databaseManager = new DatabaseManager();
    }
    @Test
    public void createDatabase(){
        databaseManager.createDatabase();
    }

/*    @Test
    public void dropDatabase(){
        databaseManager.dropDatabase();
    }*/
}
