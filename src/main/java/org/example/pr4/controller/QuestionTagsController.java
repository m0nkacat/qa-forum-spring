package org.example.pr4.controller;

import org.example.pr4.model.QuestionTagsModel;
import org.example.pr4.service.QuestionTagsService;
import org.example.pr4.service.QuestionsService;
import org.example.pr4.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question-tags")
public class QuestionTagsController {

    @Autowired
    private QuestionTagsService questionTagsService;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private TagsService tagsService;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("qt", questionTagsService.findPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("allQuestions", questionsService.findAll());
        model.addAttribute("allTags", tagsService.findAll());
        return "questiontags";
    }

    @PostMapping("/add")
    public String add(@RequestParam String questionTitle,
                      @RequestParam String tagName,
                      Model model) {
        if (questionTitle.isBlank() || tagName.isBlank()) {
            model.addAttribute("errorMessage", "Выберите вопрос и тег");
            model.addAttribute("qt", questionTagsService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allQuestions", questionsService.findAll());
            model.addAttribute("allTags", tagsService.findAll());
            return "questiontags";
        }
        try {
            questionTagsService.add(questionTitle, tagName);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("qt", questionTagsService.findPage(1));
            model.addAttribute("currentPage", 1);
            model.addAttribute("allQuestions", questionsService.findAll());
            model.addAttribute("allTags", tagsService.findAll());
            return "questiontags";
        }
        return "redirect:/question-tags";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        questionTagsService.delete(id);
        return "redirect:/question-tags";
    }
}