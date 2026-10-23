package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.CommentsModel;
import org.example.pr4.repository.CommentsRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Управление комментариями")
@SecurityRequirement(name = "bearerAuth")
public class CommentsApiController {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersRepository answersRepository;

    @GetMapping
    @Operation(summary = "Получить все комментарии")
    public List<CommentsModel> getAll() {
        return commentsRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить комментарий по ID")
    public CommentsModel getById(@PathVariable int id) {
        return commentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать комментарий")
    public CommentsModel create(@Valid @RequestBody CommentsModel comment) {
        userRepository.findById(comment.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (comment.getQuestion() != null && comment.getQuestion().getQuestionId() != null) {
            questionsRepository.findById(comment.getQuestion().getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
        }
        if (comment.getAnswer() != null && comment.getAnswer().getAnswerId() != null) {
            answersRepository.findById(comment.getAnswer().getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
        }
        return commentsRepository.save(comment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить комментарий")
    public CommentsModel update(@PathVariable int id, @Valid @RequestBody CommentsModel updated) {
        CommentsModel c = commentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        c.setBody(updated.getBody());
        userRepository.findById(updated.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        c.setUser(updated.getUser());
        if (updated.getQuestion() != null && updated.getQuestion().getQuestionId() != null) {
            questionsRepository.findById(updated.getQuestion().getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            c.setQuestion(updated.getQuestion());
        } else {
            c.setQuestion(null);
        }
        if (updated.getAnswer() != null && updated.getAnswer().getAnswerId() != null) {
            answersRepository.findById(updated.getAnswer().getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
            c.setAnswer(updated.getAnswer());
        } else {
            c.setAnswer(null);
        }
        return commentsRepository.save(c);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить комментарий")
    public void delete(@PathVariable int id) {
        commentsRepository.deleteById(id);
    }
}