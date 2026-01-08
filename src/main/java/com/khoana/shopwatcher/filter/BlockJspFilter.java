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

/**
 *
 * Filter nó chặn tất cả các đường nào có chứa đuôi.jsp
 * => Tất cả đường dẫn nào cũng sẽ cần kiểm tra 
**/
@WebFilter(filterName = "BlockJspFilter", urlPatterns = {"/*"})
public class BlockJspFilter implements Filter {
    
    // ServletRequest -> request bố của Http request, SMTP request, TCP request
    
    // Nhận hết tất cả các loại request đến từ mọi loại giao thức
    //để tránh th có 1 request đến từ 1 giao thức mạng khác http xâm nhập trái phép 
    //
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            
            String uri = req.getRequestURI();
            if(uri != null && uri.endsWith(".jsp")) {
                // Cho phép truy cập Login.jsp, register.jsp, watch.jsp
                if (uri.endsWith("Login.jsp") || uri.endsWith("register.jsp") || uri.endsWith("watch.jsp")) {
                    chain.doFilter(request, response);
                    return;
                }
                resp.sendRedirect("index.html");
                return;
            }
            
            
            chain.doFilter(request, response);
        }
}
