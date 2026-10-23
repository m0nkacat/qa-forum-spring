package org.example.pr4.controller;

import jakarta.validation.Valid;
import org.example.pr4.model.AnswersModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.model.VotesModel;
import org.example.pr4.service.AnswersService;
import org.example.pr4.service.QuestionsService;
import org.example.pr4.service.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private AnswersService answersService;

    @Autowired
    private VotesService votesService;

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "new") String filter, Model model) {
        List<QuestionsModel> allQuestions = questionsService.findAll().stream()
                .sorted((a, b) -> Integer.compare(b.getQuestionId(), a.getQuestionId()))
                .toList();

        Set<Integer> answeredQuestionIds = answersService.findAll().stream()
                .filter(a -> a.getQuestion() != null && a.getQuestion().getQuestionId() != null)
                .map(a -> a.getQuestion().getQuestionId())
                .collect(Collectors.toSet());

        List<QuestionsModel> visibleQuestions;
        switch (filter) {
            case "all" -> visibleQuestions = allQuestions;
            case "unanswered" -> visibleQuestions = allQuestions.stream()
                    .filter(q -> !answeredQuestionIds.contains(q.getQuestionId()))
                    .toList();
            default -> {
                filter = "new";
                visibleQuestions = allQuestions.stream().limit(10).toList();
            }
        }

        model.addAttribute("questions", visibleQuestions);
        model.addAttribute("questionsTotal", allQuestions.size());
        model.addAttribute("questionFilter", filter);
        model.addAttribute("question", new QuestionsModel());
        return "Index";
    }


    @GetMapping("/ask-question")
    public String askQuestion(Model model) {
        model.addAttribute("question", new QuestionsModel());
        return "ask-question";
    }

    @PostMapping("/ask-question")
    public String submitQuestion(@Valid @ModelAttribute("question") QuestionsModel question,
                                 BindingResult result,
                                 Authentication authentication) {
        if (result.hasErrors()) {
            return "ask-question";
        }
        try {
            questionsService.add(question, authentication.getName());
            return "redirect:/home";
        } catch (RuntimeException e) {
            return "redirect:/ask-question?error=true";
        }
    }

    @GetMapping("/questions/{id}")
    public String question(@PathVariable int id, Model model) {
        QuestionsModel question = questionsService.findById(id);
        if (question == null) {
            model.addAttribute("infoMessage", "Вопрос не найден.");
            return "eror";
        }

        List<AnswersModel> answers = answersService.findAll().stream()
                .filter(a -> a.getQuestion() != null && id == a.getQuestion().getQuestionId())
                .toList();

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("upVotes", votesService.countByQuestionAndType(id, 1));
        model.addAttribute("downVotes", votesService.countByQuestionAndType(id, -1));
        model.addAttribute("myVote", votesService.findMyQuestionVote(id));
        model.addAttribute("answer", new AnswersModel());
        return "question";
    }

    @PostMapping("/questions/{id}/answers")
    public String addAnswer(@PathVariable int id,
                            @Valid @ModelAttribute("answer") AnswersModel answer,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/questions/" + id + "?answerError=true";
        }
        try {
            answersService.addToQuestion(answer, id);
        } catch (RuntimeException e) {
            return "redirect:/questions/" + id + "?answerError=true";
        }
        return "redirect:/questions/" + id;
    }

    @PostMapping("/questions/{id}/vote")
    public String vote(@PathVariable int id,
                       @RequestParam int voteType,
                       Authentication authentication) {
        if (voteType != 1 && voteType != -1) {
            return "redirect:/questions/" + id;
        }
        try {
            votesService.voteQuestion(id, authentication.getName(), voteType);
        } catch (RuntimeException e) {
            return "redirect:/questions/" + id + "?voteError=true";
        }
        return "redirect:/questions/" + id;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/error")
    public String eror() {
        return "eror";
    }
}
