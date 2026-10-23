package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.AnswersModel;
import org.example.pr4.repository.AnswersRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Answers", description = "Управление ответами")
@SecurityRequirement(name = "bearerAuth")
public class AnswersApiController {

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping
    @Operation(summary = "Получить все ответы")
    public List<AnswersModel> getAll() {
        return answersRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ответ по ID")
    public AnswersModel getById(@PathVariable int id) {
        return answersRepository.findById(id).orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать ответ")
    public AnswersModel create(@Valid @RequestBody AnswersModel answer) {
        if (answer.getQuestion() != null && answer.getQuestion().getQuestionId() != null) {
            questionsRepository.findById(answer.getQuestion().getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
        }
        return answersRepository.save(answer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ответ")
    public AnswersModel update(@PathVariable int id, @Valid @RequestBody AnswersModel updated) {
        AnswersModel a = answersRepository.findById(id).orElseThrow(() -> new RuntimeException("Answer not found"));
        a.setBody(updated.getBody());
        a.setAccepted(updated.isAccepted());
        if (updated.getQuestion() != null && updated.getQuestion().getQuestionId() != null) {
            questionsRepository.findById(updated.getQuestion().getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            a.setQuestion(updated.getQuestion());
        }
        return answersRepository.save(a);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ответ")
    public void delete(@PathVariable int id) {
        answersRepository.deleteById(id);
    }
}