package org.example.pr4.controller;

import org.example.pr4.model.AppRole;
import org.example.pr4.model.AppUser;
import org.example.pr4.repository.AppUserRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository legacyUserRepository;

    @GetMapping("/registration")
    public String showForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }
        if (username.isBlank() || password.length() < 4) {
            model.addAttribute("error", "Имя не пустое, пароль — минимум 4 символа");
            return "registration";
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // BCrypt-хэш
        user.setActive(true);
        user.setRoles(Set.of(AppRole.USER));

        userRepository.save(user);

        UserModel legacy = new UserModel();
        legacy.setUsername(username);
        legacy.setPassword("*");
        legacy.setRoleId(1);
        legacyUserRepository.save(legacy);

        return "redirect:/login?registered=true";
    }
}
