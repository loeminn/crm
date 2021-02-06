package com.loemin.crm.web.filter;

import com.loemin.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();
        if (path.contains("login")) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("user");
            }
            if (user != null) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
