package com.luxoft.bankapp.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by akoscinski on 2016-05-20.
 */
public class CheckLoggedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        String parameter = servletRequest.getParameter("clientName");
        String clientName = (String) session.getAttribute("clientName");
        String path = ((HttpServletRequest)servletRequest).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(path.endsWith("login.html") || parameter != null
                || path.equals("/") || clientName != null){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            response.sendRedirect("/html/atm/atmlogin.html");
        }
    }

    @Override
    public void destroy() {

    }
}
