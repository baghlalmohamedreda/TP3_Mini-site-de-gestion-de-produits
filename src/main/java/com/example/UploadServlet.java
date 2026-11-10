package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.File;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Part filePart = req.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().println("Aucun fichier sélectionné");
            return;
        }

        String fileName = new File(
                filePart.getSubmittedFileName()
        ).getName();

        String uploadPath = System.getProperty("java.io.tmpdir")
                + File.separator + fileName;

        filePart.write(uploadPath);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("Fichier uploadé : " + fileName);
    }
}