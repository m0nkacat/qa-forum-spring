package org.example.pr4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Задание: страница для exceptionHandling().accessDeniedPage("/access-denied")
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
