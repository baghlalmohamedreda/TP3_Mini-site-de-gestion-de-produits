package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        resp.getWriter().println(
                "<form method='post' action='inscription'>" +
                        "Nom : <input type='text' name='nom'><br>" +
                        "Email : <input type='text' name='email'><br>" +
                        "Age : <input type='number' name='age'><br>" +
                        "<input type='submit' value='S inscrire'>" +
                        "</form>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");

        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String ageStr = req.getParameter("age");

        if (nom == null || nom.trim().isEmpty()) {
            resp.sendRedirect("inscription?erreur=nom_vide");
            return;
        }

        if (email == null || !email.contains("@")) {
            resp.sendRedirect("inscription?erreur=email_invalide");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageStr);

            if (age < 18) {
                resp.sendRedirect("inscription?erreur=age_min");
                return;
            }

        } catch (NumberFormatException e) {
            resp.sendRedirect("inscription?erreur=age_invalide");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("utilisateur", nom);

        resp.sendRedirect("succes");
    }
}