package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.TagsModel;
import org.example.pr4.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tags", description = "Управление тегами")
@SecurityRequirement(name = "bearerAuth")
public class TagsApiController {

    @Autowired
    private TagsRepository repository;

    @GetMapping
    @Operation(summary = "Получить все теги")
    public List<TagsModel> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тег по ID")
    public TagsModel getById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать тег")
    public TagsModel create(@Valid @RequestBody TagsModel tag) {
        return repository.save(tag);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тег")
    public TagsModel update(@PathVariable int id, @Valid @RequestBody TagsModel updated) {
        TagsModel tag = repository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(updated.getName());
        return repository.save(tag);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тег")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}