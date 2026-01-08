/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoana.shopwatcher.resources.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Huy Hoàng
 */
public class OtpService {
    SecureRandom random = new SecureRandom();
    Cache<String, String> otpCache = Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES)
        .maximumSize(10_000)
        .build();
    
    public String generateAndStore(String email) {
        String otp = generateOtp();
        otpCache.put(email, otp); //Lưu otp vào bộ nhớ tạm rồi
        return otp;
    }
    
    public boolean verify(String email, String otp) {
        String stored = otpCache.getIfPresent(email); //mã đang lưu trong bộ nhớ tạm
        if (stored == null) return false; // sai email hoặc chưa có forget pasword
        boolean ok = stored.equals(otp);
        if(ok) otpCache.invalidate(email);
        return ok;
    }
    
    public String generateOtp() {
        StringBuilder sb = new  StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    //Temp: hoangchanelqbvn@gmail.com - RANDOM
    public static void main(String[] args) {
        //1. User nó sẽ gửi yêu cầu: Bố ơi, con quên password 
        OtpService otp = new OtpService();
        String sendOtpMail = otp.generateAndStore("hoangchanelqbvn@gmail.com");
        
        //2. Nhận mã và gửi mail đến cho user quên pass
        EmailService email = new EmailService();
        email.send("hoangchanelqbvn@gmail.com", "MA XAC THUC", sendOtpMail);
        
        //3. User nhập mã xác thực
        Scanner sc = new Scanner(System.in);
        String maUserNhap = sc.nextLine();
        
        //4. Xác thực xem có chuẩn hay k
        boolean oke =  otp.verify("hoangchanelqbvn@gmail.com", maUserNhap);
        if(oke) {
            System.out.println("Perfect");
        } else {
            System.out.println("NGU");
        }
    }
}
