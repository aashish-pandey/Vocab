/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vocab;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Administrator
 */
public class deleteword extends HttpServlet{
           public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        ConnectDb connect = new ConnectDb();
        home hm = new home();
        
        out.println(hm.getMaterialize());
        out.println(hm.getNavBar());
        
        HttpSession session = req.getSession();
        String email = (String)session.getAttribute("email");
        
        if(email == null){
            out.println("Error occured<br>");
        }else{
            try{
                String password = (String)req.getParameter("newPassword");
                Connection con = connect.getConnection();
                PreparedStatement stmt;
                stmt = con.prepareStatement("delete from words where email = ?");
                stmt.setString(1, email);
                stmt.execute();
                
                con.close();
            }catch(Exception e){
                out.println(e);
            }
        }
        
        RequestDispatcher rd = req.getRequestDispatcher("profile");
            rd.forward(req, res);
        
        out.close();
    }
}
