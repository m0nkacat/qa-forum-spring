package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.pr4.model.QuestionTagsModel;
import org.example.pr4.repository.QuestionTagsRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/question-tags")
@Tag(name = "QuestionTags", description = "Связи вопросов и тегов")
@SecurityRequirement(name = "bearerAuth")
public class QuestionTagsApiController {

    @Autowired
    private QuestionTagsRepository qtRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private TagsRepository tagsRepository;

    @GetMapping
    @Operation(summary = "Получить все связи")
    public List<QuestionTagsModel> getAll() {
        return qtRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить связь по ID")
    public QuestionTagsModel getById(@PathVariable int id) {
        return qtRepository.findById(id).orElseThrow(() -> new RuntimeException("QuestionTag not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать связь вопрос-тег")
    public QuestionTagsModel create(@RequestBody QuestionTagsModel qt) {
        questionsRepository.findById(qt.getQuestion().getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        tagsRepository.findById(qt.getTag().getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return qtRepository.save(qt);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить связь")
    public void delete(@PathVariable int id) {
        qtRepository.deleteById(id);
    }
}