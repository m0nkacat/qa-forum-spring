package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.AnswersModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.service.AnswersService;
import org.example.pr4.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answers")
public class AnswersController {

    @Autowired
    private AnswersService answersService;

    @Autowired
    private QuestionsService questionsService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("answers", answersService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("answer", new AnswersModel());
        model.addAttribute("allQuestions", questionsService.findAll());
        return "answers";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("answers",
                (keyword == null || keyword.isEmpty())
                        ? answersService.findAll()
                        : answersService.search(keyword));
        model.addAttribute("currentPage", 1);
        model.addAttribute("answer", new AnswersModel());
        model.addAttribute("allQuestions", questionsService.findAll());
        return "answers";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        AnswersModel answer = answersService.findById(id);
        if (answer != null) {
            model.addAttribute("answers", List.of(answer));
        } else {
            model.addAttribute("answers", List.of());
            model.addAttribute("infoMessage", "Ответ с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("answer", new AnswersModel());
        model.addAttribute("allQuestions", questionsService.findAll());
        return "answers";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("answer") AnswersModel answer,
                      BindingResult result,
                      @RequestParam("questionTitle") String questionTitle,
                      Model model) {
        if (result.hasErrors() || questionTitle.isBlank()) {
            if (questionTitle.isBlank()) model.addAttribute("errorQuestion", "Выберите вопрос");
            model.addAttribute("answers", answersService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allQuestions", questionsService.findAll());
            return "answers";
        }
        try {
            answersService.add(answer, questionTitle);
        } catch (RuntimeException e) {
            model.addAttribute("errorQuestion", e.getMessage());
            model.addAttribute("answers", answersService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allQuestions", questionsService.findAll());
            return "answers";
        }
        return "redirect:/answers";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        answersService.delete(id);
        return "redirect:/answers";
    }


}