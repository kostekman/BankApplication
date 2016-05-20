package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akoscinski on 2016-05-20.
 */
public class LoginServlet extends HttpServlet{

    public void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String clientName = req.getParameter("clientName");
        System.out.println(clientName);
        if(clientName == null){
            throw new ServletException("No client specified.");
        }
        req.getSession().setAttribute("clientName", clientName);
        resp.sendRedirect("html/atm/menu.html");
    }
}
