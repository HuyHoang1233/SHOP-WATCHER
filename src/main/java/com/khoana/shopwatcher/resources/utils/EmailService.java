/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoana.shopwatcher.resources.utils;

import jakarta.mail.Session;
import com.khoana.shopwatcher.config.EmailInformation;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author Huy Hoàng
 */
public class EmailService {
    private Session session;
    private String from;
    
    public EmailService() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EmailInformation.MAIL_HOST);
        props.put("mail.smtp.port", EmailInformation.MAIL_PORT);
        this.from = EmailInformation.MAIL_NAME;
        
        this.session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailInformation.MAIL_USERNAME, EmailInformation.APP_PASSWORD);
            }
        });
    }
    
    public void send(String to, String subject, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        EmailService em = new EmailService();
        em.send("hoangchanelqbvn@gmail.com", "LÔ", "LÔ");
    }
    
}
