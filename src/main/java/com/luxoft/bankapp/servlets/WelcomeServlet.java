package com.luxoft.bankapp.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akoscinski on 2016-05-18.
 */
public class WelcomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        out.println("Hello! I'm ATM <br>");
        out.println("<a href='html\\atm\\atmlogin.html'>Login</a>");
    }
}
