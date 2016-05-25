package com.luxoft.bankapp.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akoscinski on 2016-05-25.
 */
public class SessionsAmountServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        ServletOutputStream out = resp.getOutputStream();
        Integer sessionCounter = (Integer) req.getSession().getServletContext().getAttribute("clientsConnected");
        out.println("<html>");
        out.println("<body>");
        if(sessionCounter != null) {
            out.println("The number of sessions is: " + sessionCounter);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
