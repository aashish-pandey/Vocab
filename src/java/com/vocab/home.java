/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vocab;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class home extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        //out.println("Hello from homepage");
        HttpSession session = req.getSession();

        String email = (String) session.getAttribute("email");
        String username = (String) req.getParameter("Username");

        ConnectDb connect = new ConnectDb();

        out.println(getMaterialize());

        out.println(getNavBar() + "<br>");

        try {
            Connection con = connect.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select word, meaning from words order by word");


            out.println("<div class='container' style='margin-top: 20px;'><table><tr><th>Word</th><th>Meaning</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td></tr>");
            }


            out.println("</table></div>");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }


    }




    public String getMaterialize() {

        String materialize = "<!--Import Google Icon Font-->" +
            " <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">" +
            "<!-- Compiled and minified CSS -->\n" +
            "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">\n" +
            "\n" +
            "    <!-- Compiled and minified JavaScript -->\n" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js\"></script>\n" +
            "           ";

        return materialize;
    }

    public String getNavBar() {
        String navbar = "<nav><div class='nav-wrapper grey darken-2'>" +
            "<a href='home' class='brand-logo left '>VOCAB</a>" +
            "<ul class='right'>" +
            "<li><a href='home'>Home</a></li>" +
            "<li><a href='profile'>Profile</a></li>" +
            "<li><a href='AddWord.html'>Add Word</a></li>" +
            "<li><a href='index.html'>LogOut</a></li></ul></div></nav>";

        return navbar;
    }

}