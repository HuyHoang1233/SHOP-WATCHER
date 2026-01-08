/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.khoana.shopwatcher.controller;

import com.khoana.shopwatcher.dao.AccountDAO;
import com.khoana.shopwatcher.model.Account;
import com.khoana.shopwatcher.resources.utils.EmailService;
import com.khoana.shopwatcher.resources.utils.OtpService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

//Viết hàm loginController và RegisterController luôn

@WebServlet(name="AuthController", urlPatterns={"/register","/login", "/forget", "/vertify"})
public class AuthController extends HttpServlet {

    private final AccountDAO dao = new AccountDAO();
    private final OtpService otp = new OtpService();
    private final EmailService emailService = new EmailService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String uri = request.getRequestURI();
       if(uri.contains("login")) {
           getLogin(request, response);
       } else if (uri.contains("register")) {
           getRegister(request, response);
       } else if (uri.contains("forget")) {
           getForgetpass(request, response);
       } else if (uri.contains("vertify")) {
           getVertify(request, response);
       }
    }
    
    private void getVertify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forget-vertify.jsp").forward(request, response);
    }
    
    private void getLogin(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
    
    private void getRegister(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void getForgetpass(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("forget-email.jsp").forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            postLogin(request, response);
        } else if (uri.contains("register")) {
            postRegister(request, response);
        } else if (uri.contains("forget")) {
            postForget(request, response);
        } else if (uri.contains("vertify")) {
            postVerify(request, response);
        }
    }
    
    
      protected void postForget(HttpServletRequest request, HttpServletResponse response)

        throws ServletException, IOException {

           String email = request.getParameter("email");
           String stored = otp.generateAndStore(email);
           emailService.send(email, "MA XAC THUC", stored);
           HttpSession session = request.getSession();
           session.setAttribute("EMAIL", email);
           
           response.sendRedirect("vertify");
           
        }

      protected void postRegister(HttpServletRequest request, HttpServletResponse response)

        throws ServletException, IOException {

            try {

                // 1. Lấy dữ liệu từ form

                String username = request.getParameter("username");

                String password = request.getParameter("password");

                String confirm  = request.getParameter("confirm"); // Lấy Confirm Password

                String email    = request.getParameter("email");



                // 2. Validate: Kiểm tra mật khẩu xác nhận

                if (password == null || !password.equals(confirm)) {

                    request.setAttribute("error", "Password wrong!");

                    // Giữ lại thông tin đã nhập

                    request.setAttribute("username", username);

                    request.setAttribute("email", email);

                    request.getRequestDispatcher("register.jsp").forward(request, response);

                    return;

                }



                // 3. Validate: Kiểm tra username đã tồn tại chưa

                if(dao.findByUserName(username) != null) {

                    request.setAttribute("error", "Username existed!");

                    request.getRequestDispatcher("register.jsp").forward(request, response);

                    return;

                } 



                // 4. Tạo tài khoản mới

                Account acc = new Account();

                acc.setEmail(email);

                acc.setUsername(username);



                // Mã hóa password

                String pwdHash = BCrypt.hashpw(password, BCrypt.gensalt());    

                acc.setPassword(pwdHash);



                // Lưu xuống DB

                dao.create(acc);



                // 5. Chuyển hướng sang trang Login

                response.sendRedirect("Login.jsp");



            } catch (RuntimeException e) {
                // Xử lý lỗi database connection
                e.printStackTrace();
                String errorMsg = e.getMessage();
                if (errorMsg != null && errorMsg.contains("Database connection failed")) {
                    request.setAttribute("error", "Cannot connect to database. Please check SQL Server configuration. See server logs for details.");
                } else {
                    request.setAttribute("error", "Error: " + errorMsg);
                }
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } catch (Exception e) {
                // Xử lý lỗi hệ thống khác
                e.printStackTrace();
                request.setAttribute("error", "System error: " + e.getMessage());
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        }
      
        protected void postLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember"); 

            Account acc = dao.findByUserName(username);

            if (acc != null && BCrypt.checkpw(password, acc.getPassword())) {
                // 1. Tạo Session
                HttpSession session = request.getSession();
                session.setAttribute("account", acc);

                // 2. Tạo Cookie (Remember Me)
                Cookie cUser = new Cookie("c_user", username);
                if (remember != null) {
                    cUser.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                } else {
                    cUser.setMaxAge(0); // Xóa cookie
                }
                response.addCookie(cUser);

                response.sendRedirect("index.html"); // Hoặc home.jsp tùy bạn
            } else {
                request.setAttribute("error", "Username or Password wrong!");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        }

    protected void postVerify(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {    
        String otpUser = request.getParameter("otp");
        String email = request.getParameter("email");
        boolean oke = otp.verify(email, otpUser);
        if(oke) {
            response.sendRedirect("index.html");
        } else {
            request.setAttribute("error", "THAT BAI");
            request.getRequestDispatcher("forget-vertify.jsp").forward(request, response);
        }
        
    }


}
