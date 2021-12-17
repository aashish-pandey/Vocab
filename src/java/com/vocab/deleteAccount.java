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
public class deleteAccount extends HttpServlet{
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
            out.println("Error occured<br>");
        }else{
            try{
                String password = (String)req.getParameter("newPassword");
                Connection con = connect.getConnection();
                PreparedStatement stmt;
                stmt = con.prepareStatement("delete from user where username = ?");
                stmt.setString(1, username);
                stmt.execute();
                
                con.close();
            }catch(Exception e){
                out.println(e);
            }
        }
        
        RequestDispatcher rd = req.getRequestDispatcher("index.html");
            rd.forward(req, res);
        
        out.close();
    }
    
}
