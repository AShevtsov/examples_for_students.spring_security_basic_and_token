package com.students;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SprintSecurityApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SprintSecurityApplication.class, args);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    }

}
