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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class reg extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        String username = (String) req.getParameter("name");
        String email = (String) req.getParameter("email");
        String password = (String) req.getParameter("password");
        try {
            Connection con = getConnection();
            PreparedStatement stmt;
            stmt = con.prepareStatement("Select username from user where username = ?");
            stmt.setString(1, username);

            ResultSet rs;
            rs = stmt.executeQuery();

            if (!rs.next()) {
                stmt = con.prepareStatement("insert into user values(?,?,?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.execute();
                out.println("Data entered received successfully");

                req.setAttribute("email", email);
                HttpSession session = req.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("username", username);
                RequestDispatcher rd = req.getRequestDispatcher("home");
                rd.forward(req, res);
            } else {
                out.println(rs);
                out.println("User with Username already exists");
                out.println("<a href='register.html'>Click me</a> to return to register page");
            }

        } catch (Exception ex) {
            out.println(ex);
        }


    }

    public com.mysql.jdbc.Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vocab", "root", "");

        return (com.mysql.jdbc.Connection) con;
    }

}