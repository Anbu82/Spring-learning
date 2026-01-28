package com.springboot.orders.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Plain password you want to use
        String rawPassword = "password";

        // Generate BCrypt hash
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Raw Password    : " + rawPassword);
        System.out.println("BCrypt Password : " + encodedPassword);
    }
}
