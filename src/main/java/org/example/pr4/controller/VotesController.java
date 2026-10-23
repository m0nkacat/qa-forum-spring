package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.VotesModel;
import org.example.pr4.service.VotesService;
import org.example.pr4.service.UserService;
import org.example.pr4.service.QuestionsService;
import org.example.pr4.service.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/votes")
public class VotesController {

    @Autowired
    private VotesService votesService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private AnswersService answersService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("votes", votesService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("vote", new VotesModel());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allAnswers", answersService.findAll());
        return "votes";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        VotesModel vote = votesService.findById(id);
        if (vote != null) {
            model.addAttribute("votes", List.of(vote));
        } else {
            model.addAttribute("votes", List.of());
            model.addAttribute("infoMessage", "Голос с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("vote", new VotesModel());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allAnswers", answersService.findAll());
        return "votes";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("vote") VotesModel vote,
                      BindingResult result,
                      @RequestParam("username") String username,
                      @RequestParam("questionTitle") String questionTitle,
                      @RequestParam(value = "answerId", required = false) Integer answerId,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("votes", votesService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allUsers", userService.findAll());
            model.addAttribute("allQuestions", questionsService.findAll());
            model.addAttribute("allAnswers", answersService.findAll());
            return "votes";
        }
        votesService.add(vote, username, questionTitle, answerId);
        return "redirect:/votes";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        votesService.delete(id);
        return "redirect:/votes";
    }
}