package com.example.UserSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserSystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserSystemApplication.class, args);
        int port = context.getEnvironment().getProperty("local.server.port", Integer.class, 0);
        System.out.println("✅ Application started on port: " + port);
    }
}



