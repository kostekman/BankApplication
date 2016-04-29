package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

    @Override
    public Client findClientByName(int bankId, String name) throws DAOException, TooManyClientsFoundException, ClientNotFoundException {
        String sqlSelect =  "SELECT C.ID, C.NAME, C.CITY, C.GENDER, C.PHONENUMBER," +
                " C.EMAIL, C.INITIALOVERDRAFT FROM CLIENTS AS C" +
                " INNER JOIN BANK_CLIENTS AS BC" +
                " ON C.ID = BC.CLIENT_ID" +
                " INNER JOIN BANKS AS B" +
                " ON BC.BANK_ID = B.ID" +
                " WHERE B.ID = ? AND C.NAME = ?";

        PreparedStatement stmt;
        Client client;

        try{

            int counter = countClientFindByNameResults(bankId, name);
            if(counter == 0){
                throw new ClientNotFoundException(bankId, name);
            }
            else if(counter > 1){
                throw new TooManyClientsFoundException(counter, bankId, name);
            }
            else {
                stmt = conn.prepareStatement(sqlSelect);
                stmt.setInt(1, bankId);
                stmt.setString(2, name);
                ResultSet clients = stmt.executeQuery();
                Gender gender;
                if (clients.getBoolean(3)) {
                    gender = Gender.MALE;
                } else {
                    gender = Gender.FEMALE;
                }
                client = new Client(clients.getString(2),
                        clients.getString(3), gender, clients.getString(5),
                        clients.getString(6), clients.getFloat(7));
                client.setId(clients.getInt(1));
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int countClientFindByNameResults(int bankId, String name) {
        String sqlCount = "SELECT COUNT(*) FROM CLIENTS AS C" +
                " INNER JOIN BANK_CLIENTS AS BC" +
                " ON C.ID = BC.CLIENT_ID" +
                " INNER JOIN BANKS AS B" +
                " ON BC.BANK_ID = B.ID" +
                " WHERE B.ID = ? AND C.NAME = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlCount);
            stmt.setInt(1, bankId);
            stmt.setString(2, name);
            ResultSet countedClients = stmt.executeQuery();
            closeConnection();
            return countedClients.getInt(1);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Client> getAllClients(int bankId) throws DAOException {
        String sqlSelect =  "SELECT C.ID, C.NAME, C.CITY, C.GENDER, C.PHONENUMBER," +
                            " C.EMAIL, C.INITIALOVERDRAFT FROM CLIENTS AS C" +
                            " INNER JOIN BANK_CLIENTS AS BC" +
                            " ON C.ID = BC.CLIENT_ID" +
                            " INNER JOIN BANKS AS B" +
                            " ON BC.BANK_ID = B.ID" +
                            " WHERE B.ID = ?";
        PreparedStatement stmt;
        List<Client> resultClientList = new LinkedList<>();
        try{
            openConnection();
            stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, bankId);
            ResultSet clients = stmt.executeQuery();
            while(clients.next()){
                Gender gender;
                if(clients.getBoolean(3)){
                    gender = Gender.MALE;
                }
                else{
                    gender = Gender.FEMALE;
                }
                resultClientList.add(new Client(clients.getString(1),
                        clients.getString(2), gender, clients.getString(4),
                        clients.getString(5), clients.getFloat(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally{
            closeConnection();
        }
        return resultClientList;
    }

    @Override
    public void save(Client client, int bankId) throws DAOException {
        String sqlInsertClient = "INSERT INTO CLIENTS VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlInsertClient);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCity());
            stmt.setInt(3, client.getGender().toInt());
            stmt.setString(4, client.getPhoneNumber());
            stmt.setString(5, client.getEmail());
            stmt.setFloat(6, client.getInitialOverdraft());
            stmt.execute();
            closeConnection();
            client.setId(findClientByName(bankId, client.getName()).getId());
            addToBankClientsTable(bankId, client.getId());
        } catch (SQLException | ClientNotFoundException | TooManyClientsFoundException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }

    private void addToBankClientsTable(int bankId, int clientId) {
        String sqlInsertBankClients = "INSERT INTO BANK_CLIENTS VALUES(?, ?);";
        PreparedStatement stmt;
        try{
            openConnection();
            stmt = conn.prepareStatement(sqlInsertBankClients);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.execute();
            closeConnection();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remove(int clientId, int bankId) throws DAOException {
        String sqlRemoveClient =    "REMOVE COLUMN FROM TABLE BANK_CLIENTS" +
                                    " WHERE BANK_ID = ? AND CLIENT_ID = ?";
        PreparedStatement stmt;
        try{
            openConnection();
            stmt = conn.prepareStatement(sqlRemoveClient);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.execute();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
