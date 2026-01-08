/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.khoana.shopwatcher.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="LogoutController", urlPatterns={"/logout"})
public class LogoutController extends HttpServlet {

    // Xử lý khi người dùng bấm vào link <a href="logout">Logout</a> (Method GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // 1. Hủy Session (Xóa phiên làm việc phía Server)
        HttpSession session = request.getSession(false); // false nghĩa là: nếu chưa có session thì đừng tạo mới
        if (session != null) {
            session.invalidate(); // Hủy session hiện tại
        }

        // 2. Xóa Cookie (Xóa dấu vết phía Client)
        // Lưu ý: Tên cookie "c_user" phải GIỐNG HỆT tên bạn đã đặt bên LoginController
        Cookie c = new Cookie("c_user", ""); 
        c.setMaxAge(0); // Set thời gian sống = 0 để trình duyệt xóa nó ngay lập tức
        response.addCookie(c); // Gửi lệnh xóa về trình duyệt

        // 3. Quay về trang login
        response.sendRedirect("Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Nếu lỡ có ai gửi request POST logout, cũng xử lý y hệt như GET
        doGet(request, response);
    }

}