package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.service.QuestionsService;
import org.example.pr4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("questions", questionsService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("question", new QuestionsModel());
        model.addAttribute("allUsers", userService.findAll());
        return "questions";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("questions",
                (keyword == null || keyword.isEmpty())
                        ? questionsService.findAll()
                        : questionsService.search(keyword));
        model.addAttribute("currentPage", 1);
        model.addAttribute("question", new QuestionsModel());
        model.addAttribute("allUsers", userService.findAll());
        return "questions";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        QuestionsModel question = questionsService.findById(id);
        if (question != null) {
            model.addAttribute("questions", List.of(question));
        } else {
            model.addAttribute("questions", List.of());
            model.addAttribute("infoMessage", "Вопрос с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("question", new QuestionsModel());
        model.addAttribute("allUsers", userService.findAll());
        return "questions";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        questionsService.delete(id);
        return "redirect:/questions";
    }
}