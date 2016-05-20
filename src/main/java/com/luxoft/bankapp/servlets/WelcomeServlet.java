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
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>");
        out.println("<body>");
        out.println("Hello! I'm ATM <br>");
        out.println("<a href='html\\atm\\atmlogin.html'>Login</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
