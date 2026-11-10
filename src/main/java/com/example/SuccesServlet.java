package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/succes")
public class SuccesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("utilisateur") == null) {
            resp.sendRedirect(req.getContextPath() + "/inscription");
            return;
        }

        String utilisateur = (String) session.getAttribute("utilisateur");

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h1>Bienvenue " + utilisateur + " !</h1>");
    }
}