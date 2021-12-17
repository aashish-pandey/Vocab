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
public class changePassword extends HttpServlet{
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        ConnectDb connect = new ConnectDb();
        home hm = new home();
        
        out.println(hm.getMaterialize());
        out.println(hm.getNavBar());
        
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");
        
        if(username == null){
            out.println("Please login to continue<br>");
            RequestDispatcher rd = req.getRequestDispatcher("index.html");
        rd.forward(req, res);
        }else{
            try{
                String password = (String)req.getParameter("newPassword");
                Connection con = connect.getConnection();
                PreparedStatement stmt;
                stmt = con.prepareStatement("update user set password = ? where username = ?");
                stmt.setString(1, password);
                stmt.setString(2, username);
                
                stmt.execute();
                
                con.close();
            RequestDispatcher rd = req.getRequestDispatcher("profile");
            rd.forward(req, res);
            }catch(Exception e){
                out.println(e);
            }
        }
        
        out.close();
    }
    
}
