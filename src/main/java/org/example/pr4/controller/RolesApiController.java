package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.RolesModel;
import org.example.pr4.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Управление ролями")
@SecurityRequirement(name = "bearerAuth")
public class RolesApiController {

    @Autowired
    private RolesRepository repository;

    @GetMapping
    @Operation(summary = "Получить все роли")
    public List<RolesModel> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить роль по ID")
    public RolesModel getById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать роль")
    public RolesModel create(@Valid @RequestBody RolesModel role) {
        return repository.save(role);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить роль")
    public RolesModel update(@PathVariable int id, @Valid @RequestBody RolesModel updated) {
        RolesModel role = repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(updated.getName());
        return repository.save(role);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить роль")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}