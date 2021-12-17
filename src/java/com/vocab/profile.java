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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class profile extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ConnectDb connect = new ConnectDb();
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();

        home hm = new home();
        out.println(hm.getMaterialize());
        out.println(hm.getNavBar());

        String name = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");


        String password = null;

        try {
            Connection con = connect.getConnection();
            PreparedStatement stmt = con.prepareStatement("select password from user where username = ?");
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);

            }
        } catch (Exception e) {
            out.println(e);
        }

        out.println("<div class='container'>");
        
        
        out.println("<table>\n" +
"        <thead>\n" +
"          <tr>\n" +
"              <th>Email</th>\n" +
"              <th>Name</th>\n" +
"              <th>Password</th>\n" +
"          </tr>\n" +
"        </thead>\n" +
"\n" +
"        <tbody>\n" +
"          <tr>\n" +
"            <td>" + email + "</td>\n" +
"            <td>"+ name + "</td>\n" +
"            <td>"+ password +"</td>\n" +
"          </tr>\n" +
"        </tbody>\n" +
"      </table>");

        out.println("<div style='padding: 3%; margin-left: 16%;'><a class='btn waves-effect waves-light center' href='changePass.html'>Change my password</a>");
        out.println("<a class='btn waves-effect waves-light center' href='deleteAccount.html'>Delete my account</a></div></div>");
        
        try{
        Connection con = connect.getConnection();
        PreparedStatement stmt = con.prepareStatement("select word, meaning from words where email = ?");
        stmt.setString(1, email);
         ResultSet rs = stmt.executeQuery();
         
         out.println("<div class='container' style='margin-top: 20px;'>");
         out.println("<h5>Words added by you are: </h5><br><br>");
        out.println("<table><tr><th>Word</th><th>Meaning</th></tr>");
        
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td><a class='btn waves-effect waves-light center' href='deleteWord'>Delete Word</a></td></tr>");
            }


            out.println("</table></div>");
        
        
        }catch(Exception e){
            out.println(e);
        }
        
        
    }



}