package com.luxoft.bankapp.databasemanagement;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.loggers.CurrentDateAndTime;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

    @Override
    public Client findClientByName(String name) throws DAOException, TooManyClientsFoundException, ClientNotFoundException {
        String sqlSelect = "SELECT C.ID, C.NAME, C.CITY, C.GENDER, C.PHONENUMBER," +
                " C.EMAIL, C.INITIALOVERDRAFT FROM CLIENTS AS C" +
                " WHERE C.NAME = ?";

        PreparedStatement stmt;
        Client client = null;

        try {

            int counter = countClientFindByNameResults(name);
            if (counter == 0) {
                throw new ClientNotFoundException(name);
            } else if (counter > 1) {
                throw new TooManyClientsFoundException(counter, name);
            } else {
                openConnection();
                stmt = conn.prepareStatement(sqlSelect);
                stmt.setString(1, name);
                ResultSet clients = stmt.executeQuery();
                Gender gender;
                while (clients.next()) {
                    if (clients.getBoolean(4)) {
                        gender = Gender.MALE;
                    } else {
                        gender = Gender.FEMALE;
                    }
                    client = new Client(clients.getString(2),
                            clients.getString(3), gender, clients.getString(5),
                            clients.getString(6), clients.getFloat(7));
                    client.setId(clients.getInt(1));
                }
                closeConnection();
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        }

        return null;
    }

    private int countClientFindByNameResults(String name) {
        String sqlCount = "SELECT COUNT(*) FROM CLIENTS AS C" +
                " WHERE C.NAME = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlCount);
            stmt.setString(1, name);
            ResultSet countedClients = stmt.executeQuery();
            int counter = 0;
            while (countedClients.next()) {
                counter = countedClients.getInt(1);
            }
            return counter;
        } catch (DAOException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        } finally {
            closeConnection();
        }
        return 0;
    }

    @Override
    public List<Client> getAllClients(int bankId) throws DAOException {
        String sqlSelect = "SELECT C.ID, C.NAME, C.CITY, C.GENDER, C.PHONENUMBER," +
                " C.EMAIL, C.INITIALOVERDRAFT FROM CLIENTS AS C" +
                " INNER JOIN BANK_CLIENTS AS BC" +
                " ON C.ID = BC.CLIENT_ID" +
                " INNER JOIN BANKS AS B" +
                " ON BC.BANK_ID = B.ID" +
                " WHERE B.ID = ?";
        PreparedStatement stmt;
        List<Client> resultClientList = new LinkedList<>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, bankId);
            ResultSet clients = stmt.executeQuery();
            while (clients.next()) {
                Gender gender;
                if (clients.getBoolean(4)) {
                    gender = Gender.MALE;
                } else {
                    gender = Gender.FEMALE;
                }
                Client client = new Client(clients.getString(2),
                        clients.getString(3), gender, clients.getString(5),
                        clients.getString(6), clients.getFloat(7));
                client.setId(clients.getInt(1));
                resultClientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return resultClientList;
    }

    @Override
    public void save(Client client, int bankId) throws DAOException {
        String sqlInsertClient = "INSERT INTO CLIENTS(NAME, CITY, GENDER," +
                "PHONENUMBER, EMAIL, INITIALOVERDRAFT) " +
                "VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt;
        try {
            if (countClientFindByNameResults(client.getName()) == 1) {
                addToBankClientsTable(bankId, client.getId());
                BankAppLogger.log(CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Client with id: " + client.getId() + "added to bank with id: " + bankId);
            } else {
                openConnection();
                stmt = conn.prepareStatement(sqlInsertClient);
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getCity());
                stmt.setBoolean(3, client.getGender().toBoolean());
                stmt.setString(4, client.getPhoneNumber());
                stmt.setString(5, client.getEmail());
                stmt.setFloat(6, client.getInitialOverdraft());
                stmt.execute();
                closeConnection();
                client.setId(findClientByName(client.getName()).getId());
                addToBankClientsTable(bankId, client.getId());
                closeConnection();
                BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Client with id: " + client.getId() + "created and added to bank with id: " + bankId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (TooManyClientsFoundException e) {
            e.printStackTrace();
        }
    }

    private void addToBankClientsTable(int bankId, int clientId) {
        String sqlInsertBankClients = "INSERT INTO BANK_CLIENTS VALUES(?, ?);";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlInsertBankClients);
            stmt.setInt(1, bankId);
            stmt.setInt(2, clientId);
            stmt.execute();
            closeConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        }
    }


    @Override
    public void remove(int clientId, int bankId) throws DAOException {
        String sqlCountClients = "SELECT COUNT(*) FROM BANK_CLIENTS" +
                " WHERE CLIENT_ID = ?";
        PreparedStatement stmt;
        AccountDAO accountDAO = new AccountDAOImpl();

        int counter = 0;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlCountClients);
            stmt.setInt(1, clientId);
            ResultSet counterResult = stmt.executeQuery();
            while (counterResult.next()) {
                counter = counterResult.getInt(1);
            }
            closeConnection();

            accountDAO.removeClientAccounts(bankId, clientId);
            removeClientFromBank(bankId, clientId);
            if (counter == 1) {
                removeClientFromDatabase(clientId);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientFromBank(int bankId, int clientId) throws DAOException {
        String sqlRemoveClient = "DELETE FROM BANK_CLIENTS" +
                " WHERE CLIENT_ID = ? AND BANK_ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlRemoveClient);
            stmt.setInt(1, clientId);
            stmt.setInt(2, bankId);
            stmt.execute();

            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Client with id: " + clientId + "removed from bank with id: " + bankId);


        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientFromDatabase(int clientId) throws DAOException {
        String sqlRemoveClient = "DELETE FROM CLIENTS WHERE ID = ?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sqlRemoveClient);
            stmt.setInt(1, clientId);
            stmt.execute();
            closeConnection();
            BankAppLogger.log("DB " + CurrentDateAndTime.getCurrentDateAndTime() + " | " + "Client with id: " + clientId + "removed from database");

        } catch (SQLException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            throw new DAOException();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
