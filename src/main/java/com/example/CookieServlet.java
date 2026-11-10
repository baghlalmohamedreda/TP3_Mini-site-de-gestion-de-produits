package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if ("set".equals(action)) {
            Cookie cookie = new Cookie("theme", "dark");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath(req.getContextPath() + "/");
            resp.addCookie(cookie);
        }

        resp.setContentType("text/html;charset=UTF-8");

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                resp.getWriter().println(
                        c.getName() + " : " + c.getValue() + "<br>"
                );
            }
        }
    }
}