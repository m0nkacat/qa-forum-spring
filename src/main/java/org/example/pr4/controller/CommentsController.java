package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.CommentsModel;
import org.example.pr4.service.CommentsService;
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
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private AnswersService answersService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("comments", commentsService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("comment", new CommentsModel());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allAnswers", answersService.findAll());
        return "comments";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("comments",
                (keyword == null || keyword.isEmpty())
                        ? commentsService.findAll()
                        : commentsService.search(keyword));
        model.addAttribute("currentPage", 1);
        model.addAttribute("comment", new CommentsModel());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allAnswers", answersService.findAll());
        return "comments";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        CommentsModel comment = commentsService.findById(id);
        if (comment != null) {
            model.addAttribute("comments", List.of(comment));
        } else {
            model.addAttribute("comments", List.of());
            model.addAttribute("infoMessage", "Комментарий с ID " + id + " не найден.");
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("comment", new CommentsModel());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allAnswers", answersService.findAll());
        return "comments";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("comment") CommentsModel comment,
                      BindingResult result,
                      @RequestParam("username") String username,
                      @RequestParam(value = "questionTitle", required = false) String questionTitle,
                      @RequestParam(value = "answerId", required = false) Integer answerId,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comments", commentsService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allUsers", userService.findAll());
            model.addAttribute("allQuestions", questionsService.findAll());
            model.addAttribute("allAnswers", answersService.findAll());
            return "comments";
        }
        commentsService.add(comment, username, questionTitle, answerId);
        return "redirect:/comments";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        commentsService.delete(id);
        return "redirect:/comments";
    }
}