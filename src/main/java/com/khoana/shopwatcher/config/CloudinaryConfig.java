/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoana.shopwatcher.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.ServletContext;

/**
 *
 * @author Huy Hoàng
 */
public class CloudinaryConfig {
    private final Cloudinary cloudinary;
    
    public CloudinaryConfig(ServletContext ctx) {
        this.cloudinary = new  Cloudinary(ObjectUtils.asMap(
                "cloud_name", ctx.getInitParameter("CLOUD_NAME"),
                "api_key", ctx.getInitParameter("API_KEY"),
                "api_secret", ctx.getInitParameter("API_SECRET")
        ));
    }
    
    public Cloudinary getClient() {
        return cloudinary;
    }
    
}
