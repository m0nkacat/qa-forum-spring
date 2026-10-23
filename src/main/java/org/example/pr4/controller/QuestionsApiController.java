package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Questions", description = "Управление вопросами")
@SecurityRequirement(name = "bearerAuth")
public class QuestionsApiController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Получить все вопросы")
    public List<QuestionsModel> getAll() {
        return questionsRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить вопрос по ID")
    public QuestionsModel getById(@PathVariable int id) {
        return questionsRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать вопрос")
    public QuestionsModel create(@Valid @RequestBody QuestionsModel question) {
        if (question.getUser() != null && question.getUser().getUserId() != null) {
            userRepository.findById(question.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        return questionsRepository.save(question);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить вопрос")
    public QuestionsModel update(@PathVariable int id, @Valid @RequestBody QuestionsModel updated) {
        QuestionsModel q = questionsRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        q.setTitle(updated.getTitle());
        q.setBody(updated.getBody());
        if (updated.getUser() != null && updated.getUser().getUserId() != null) {
            userRepository.findById(updated.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            q.setUser(updated.getUser());
        }
        return questionsRepository.save(q);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вопрос")
    public void delete(@PathVariable int id) {
        questionsRepository.deleteById(id);
    }
}