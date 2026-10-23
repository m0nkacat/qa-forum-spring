package org.example.pr4.controller;

import org.example.pr4.model.AppRole;
import org.example.pr4.model.AppUser;
import org.example.pr4.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AppUserRepository userRepository;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("allRoles", AppRole.values());
        return "admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден: " + id));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", AppRole.values());
        return "admin/edit-user";
    }

    @PostMapping("/users/{id}/edit")
    public String saveRoles(@PathVariable Long id,
                            @RequestParam(name = "roles", required = false) String[] roles) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден: " + id));

        Set<AppRole> newRoles = new HashSet<>();
        if (roles != null) {
            for (String r : roles) {
                try { newRoles.add(AppRole.valueOf(r)); }
                catch (IllegalArgumentException ignored) {}
            }
        }
        if (newRoles.isEmpty()) newRoles.add(AppRole.USER);

        user.setRoles(newRoles);
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle-active")
    public String toggleActive(@PathVariable Long id,
                               @RequestParam(name = "reason", required = false) String reason) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден: " + id));
        boolean newActive = !user.isActive();
        user.setActive(newActive);

        if (newActive) {
            user.setBlockReason(null);
        } else {
            String normalizedReason = reason == null ? "" : reason.trim();
            user.setBlockReason(normalizedReason.isBlank()
                    ? "Нарушение правил сайта."
                    : normalizedReason);
        }

        userRepository.save(user);
        return "redirect:/admin/users";
    }
}
