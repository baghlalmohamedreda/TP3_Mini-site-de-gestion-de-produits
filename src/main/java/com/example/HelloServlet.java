
package com.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String machineName;

        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            machineName = "Nom de machine indisponible";
        }

        LocalDateTime dateTime = LocalDateTime.now();

        String userAgent = request.getHeader("User-Agent");

        PrintWriter out = response.getWriter();

        out.println("<h1>Informations du serveur</h1>");
        out.println("<p>Nom de la machine : " + machineName + "</p>");
        out.println("<p>Date et heure : " + dateTime + "</p>");
        out.println("<p>User-Agent : " + userAgent + "</p>");
    }
}
