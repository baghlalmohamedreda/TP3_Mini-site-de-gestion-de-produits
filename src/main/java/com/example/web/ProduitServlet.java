package com.example.web;

import com.example.model.Produit;
import com.example.model.ProduitDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private static final String VIEWS = "/WEB-INF/views/produits/";
    private ProduitDAO dao;

    @Override
    public void init() {
        dao = (ProduitDAO) getServletContext().getAttribute("produitDAO");
    }

    // ---------------------------------------------------------------
    //  GET : lister / formulaire vide / formulaire pré-rempli
    // ---------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.setAttribute("produit", new Produit());
            req.getRequestDispatcher(VIEWS + "form.jsp").forward(req, resp);

        } else if ("edit".equals(action)) {
            Long id = parseId(req.getParameter("id"));
            Produit p = dao.findById(id);
            if (p == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("produit", p);
            req.getRequestDispatcher(VIEWS + "form.jsp").forward(req, resp);

        } else {
            req.setAttribute("produits", dao.findAll());
            req.getRequestDispatcher(VIEWS + "list.jsp").forward(req, resp);
        }
    }

    // ---------------------------------------------------------------
    //  POST : supprimer / créer / modifier
    // ---------------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // ----- Suppression -----
        if ("delete".equals(req.getParameter("action"))) {
            Long id = parseId(req.getParameter("id"));
            Produit aSupprimer = dao.findById(id);
            if (aSupprimer != null) {
                dao.delete(id);
                session.setAttribute("flash",
                        "Produit « " + aSupprimer.getNom() + " » supprimé.");
            }
            resp.sendRedirect(req.getContextPath() + "/produits");
            return;
        }

        // ----- Lecture des champs -----
        String idStr    = req.getParameter("id");
        String nom      = trim(req.getParameter("nom"));
        String desc     = req.getParameter("description");
        String prixStr  = trim(req.getParameter("prix"));
        String stockStr = trim(req.getParameter("stock"));

        // ----- Validation -----
        Map<String, String> erreurs = new HashMap<>();

        if (nom.length() < 2 || nom.length() > 50) {
            erreurs.put("nom", "Le nom est obligatoire (2 à 50 caractères).");
        }

        BigDecimal prix = null;
        if (prixStr.isEmpty()) {
            erreurs.put("prix", "Le prix est obligatoire.");
        } else {
            try {
                prix = new BigDecimal(prixStr.replace(',', '.'));
                if (prix.signum() <= 0) {
                    erreurs.put("prix", "Le prix doit être strictement positif.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("prix", "Le prix doit être un nombre décimal.");
            }
        }

        int stock = 0;
        if (!stockStr.isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    erreurs.put("stock", "Le stock doit être un entier supérieur ou égal à 0.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("stock", "Le stock doit être un nombre entier.");
            }
        }

        if (desc != null && desc.length() > 255) {
            erreurs.put("description", "La description ne doit pas dépasser 255 caractères.");
        }

        // ----- Construction de l'objet -----
        Produit p = new Produit();
        if (idStr != null && !idStr.isBlank()) {
            Long id = parseId(idStr);
            if (id == null || dao.findById(id) == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            p.setId(id);
        }
        p.setNom(nom);
        p.setDescription(desc);
        p.setPrix(prix);
        p.setStock(stock);

        // ----- Erreurs : FORWARD (on garde les valeurs saisies) -----
        if (!erreurs.isEmpty()) {
            req.setAttribute("erreurs", erreurs);
            req.setAttribute("produit", p);
            req.getRequestDispatcher(VIEWS + "form.jsp").forward(req, resp);
            return;
        }

        // ----- Succès : save + flash + REDIRECT (pattern PRG) -----
        dao.save(p);
        session.setAttribute("flash", "Produit « " + p.getNom() + " » enregistré.");
        resp.sendRedirect(req.getContextPath() + "/produits");
    }

    // ---------------------------------------------------------------
    //  Utilitaires
    // ---------------------------------------------------------------
    private static String trim(String s) {
        return (s == null) ? "" : s.trim();
    }

    private static Long parseId(String s) {
        try {
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            return null; // null ou texte invalide
        }
    }
}