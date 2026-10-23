package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.RolesModel;
import org.example.pr4.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesService service;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("roles", service.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("role", new RolesModel());
        return "roles";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {

        model.addAttribute("roles",
                (keyword == null || keyword.isEmpty())
                        ? service.findAll()
                        : service.search(keyword));

        model.addAttribute("currentPage", 1);
        model.addAttribute("role", new RolesModel());

        return "roles";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("role") RolesModel role,
                      BindingResult result,
                      Model model) {

        if (result.hasErrors()) {
            model.addAttribute("roles", service.findAll());
            model.addAttribute("currentPage", 1);
            return "roles";
        }

        service.add(role);
        return "redirect:/roles";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        service.delete(id);
        return "redirect:/roles";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {

        RolesModel role = service.findById(id);

        if (role != null) {
            model.addAttribute("roles", List.of(role));
        } else {
            model.addAttribute("roles", List.of());
        }

        model.addAttribute("currentPage", 1);
        model.addAttribute("role", new RolesModel());

        return "roles";
    }
}