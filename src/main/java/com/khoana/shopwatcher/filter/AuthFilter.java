/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.khoana.shopwatcher.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Huy Hoàng
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/watch.html"}) //trang này để chặn người dùng trước khi đăng nhập báo đăng nhập vào đi mới được xem
public class AuthFilter implements Filter {
    
   
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession();
        
        if(session == null || session.getAttribute("account") == null) {
            req.setAttribute("error", "Please login before watch.html");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        } 
        chain.doFilter(request, response);
        }
    
        
     
}
