/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoana.shopwatcher.dao;

import com.khoana.shopwatcher.model.Account;
import jakarta.persistence.*;

/**
 *
 * @author Huy Hoàng
 */
public class AccountDAO {

    // 1. Quản lý EntityManagerFactory (Singleton Pattern)
    private static EntityManagerFactory emf;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                // Tên "watcherManager" phải trùng khớp 100% với persistence.xml
                emf = Persistence.createEntityManagerFactory("watcherManager");
            } catch (Exception e) {
                // Nếu lỗi ngay lúc khởi tạo (sai pass, chưa bật TCP/IP...)
                String errorMsg = extractErrorMessage(e);
                System.err.println("=== CONNECT DATABASE FAIL! ===");
                System.err.println("ERROR: " + errorMsg);
                // Ném lỗi này ra để Controller biết
                throw new RuntimeException(errorMsg, e);
            }
        }
        return emf;
    }

    // Hàm tách thông báo lỗi cho dễ hiểu hơn
    private static String extractErrorMessage(Exception e) {
        String msg = e.getMessage();
        Throwable cause = e.getCause();
        while (cause != null) {
            msg += " | " + cause.getMessage();
            cause = cause.getCause();
        }
        
        if (msg.contains("Login failed")) {
            return "Sai Username hoặc Password trong persistence.xml (Lỗi Login failed).";
        } else if (msg.contains("The TCP/IP connection to the host")) {
            return "Chưa bật TCP/IP hoặc sai Port 1433. Hãy kiểm tra SQL Server Configuration Manager.";
        } else if (msg.contains("Cannot open database")) {
            return "Database 'WatcherDB' chưa được tạo trong SQL Server.";
        } else if (msg.contains("driver")) {
            return "Thiếu thư viện Driver JDBC (mssql-jdbc) trong pom.xml hoặc Libraries.";
        }
        return msg;
    }

    // 2. Hàm tạo tài khoản (SỬA LỖI QUAN TRỌNG: Ném Exception ra ngoài)
    public void create(Account acc) {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            em.persist(acc); 
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // QUAN TRỌNG: Không được nuốt lỗi (chỉ e.printStackTrace).
            // Phải ném lỗi ra để Controller biết đường xử lý.
            throw new RuntimeException("Lỗi khi lưu Account: " + extractErrorMessage(e), e);
        } finally {
            if (em != null) em.close();
        }
    }

    // 3. Hàm tìm kiếm tài khoản
    public Account findByUserName(String username) {
        EntityManager em = null;
        try {
            em = getEntityManagerFactory().createEntityManager();
            return em.createQuery("SELECT a From Account a WHERE a.username = :u", Account.class)
                     .setParameter("u", username)
                     .getResultStream()
                     .findFirst()
                     .orElse(null);
        } catch (Exception e) {
            // Nếu lỗi kết nối lúc tìm kiếm, in ra log
            throw new RuntimeException("Lỗi kết nối Database: " + extractErrorMessage(e));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // ==========================================================
    // HÀM MAIN ĐỂ TEST KẾT NỐI (Chạy file này bằng Shift + F6)
    // ==========================================================
    public static void main(String[] args) {
        System.out.println("--- LOADING CONNECT DATABASE ---");
        try {
            AccountDAO dao = new AccountDAO();
            // Thử tìm một user để test kết nối (không cần user thật)
            dao.findByUserName("test_connection_user_that_does_not_exist");
            System.out.println(">>> DATABASE CONNECTED! (Database ngon lành)");
        } catch (Exception e) {
            System.err.println(">>> CONNECT FAIL!");
            System.err.println("Cause: " + e.getMessage());
            e.printStackTrace();
        }
    }
}