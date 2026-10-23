package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.TagsModel;
import org.example.pr4.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService service;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("tags", service.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("tag", new TagsModel());
        return "tags";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        if (keyword == null || keyword.isBlank()) {
            model.addAttribute("tags", service.findPage(1));
        } else {
            model.addAttribute("tags", service.search(keyword));
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("tag", new TagsModel());
        return "tags";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        TagsModel tag = service.findById(id);
        if (tag != null) {
            model.addAttribute("tags", List.of(tag));
        } else {
            model.addAttribute("tags", List.of());
            model.addAttribute("infoMessage", "Тег с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("tag", new TagsModel());
        return "tags";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("tag") TagsModel tag,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tags", service.findPage(1));
            model.addAttribute("currentPage", 1);
            return "tags";
        }
        service.add(tag);
        return "redirect:/tags";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        service.delete(id);
        return "redirect:/tags";
    }
}