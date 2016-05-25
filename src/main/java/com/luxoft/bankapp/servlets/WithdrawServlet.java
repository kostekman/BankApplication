package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akoscinski on 2016-05-25.
 */
public class WithdrawServlet extends HttpServlet {

    public void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        ServletOutputStream out = resp.getOutputStream();
        String clientName = (String) req.getSession().getAttribute("clientName");
        Float amount = Float.parseFloat(req.getParameter("amount"));
        BankService bankService = new BankServiceImpl();
        try {
            Client client = bankService.loadClientFromDb(clientName);
            client.getActiveAccount().withdraw(amount);
            resp.sendRedirect("/html/atm/balance");
        } catch (TooManyClientsFoundException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (BankException e) {
            e.printStackTrace();
        }
    }
}