package com.students.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/helloAdmin")
    public String helloAdmin() {
        return "hello admin world";
    }

    @GetMapping("/helloUser")
    public String helloUser() {
        return "hello user world";
    }

}
