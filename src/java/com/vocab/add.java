/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vocab;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class add extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ConnectDb connect = new ConnectDb();
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();


        home hm = new home();
        out.println(hm.getMaterialize());
        out.println(hm.getNavBar());
        String word = (String) req.getParameter("word");
        String meaning = (String) req.getParameter("meaning");
        String email = (String) session.getAttribute("email");

        out.println(word + "  :  " + meaning + "     " + email);


        try {
            Connection con = connect.getConnection();

            PreparedStatement stmt;

            stmt = con.prepareStatement("insert into words(word, meaning, email) values(?,?,?)");

            stmt.setString(1, word);
            stmt.setString(2, meaning);
            stmt.setString(3, email);
            stmt.execute();

            out.print("<br><br>");
            RequestDispatcher rd = req.getRequestDispatcher("profile");
            rd.forward(req, res);


        } catch (Exception ex) {
            out.println("<h3> " + ex + "</h3>");
        }


    }

}