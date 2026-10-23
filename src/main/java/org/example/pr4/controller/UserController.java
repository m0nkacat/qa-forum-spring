package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.UserModel;
import org.example.pr4.service.RolesService;
import org.example.pr4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("users", userService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("user", new UserModel());
        model.addAttribute("allRoles", rolesService.findAll());
        return "users";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("users",
                (keyword == null || keyword.isEmpty())
                        ? userService.findAll()
                        : userService.search(keyword));
        model.addAttribute("currentPage", 1);
        model.addAttribute("user", new UserModel());
        model.addAttribute("allRoles", rolesService.findAll());
        return "users";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        UserModel user = userService.findById(id);
        if (user != null) {
            model.addAttribute("users", List.of(user));
        } else {
            model.addAttribute("users", List.of());
            model.addAttribute("infoMessage", "Пользователь с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("user", new UserModel());
        model.addAttribute("allRoles", rolesService.findAll());
        return "users";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("user") UserModel user,
                      BindingResult result,
                      @RequestParam("roleId") Integer roleId,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allRoles", rolesService.findAll());
            return "users";
        }
        if (rolesService.findById(roleId) == null) {
            model.addAttribute("errorRole", "Роль с ID " + roleId + " не найдена");
            model.addAttribute("users", userService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allRoles", rolesService.findAll());
            return "users";
        }
        user.setRoleId(roleId);
        userService.add(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}