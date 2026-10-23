package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.UserModel;
import org.example.pr4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Управление пользователями")
@SecurityRequirement(name = "bearerAuth")
public class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    public List<UserModel> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserModel getById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать пользователя")
    public UserModel create(@Valid @RequestBody UserModel user) {
        return repository.save(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    public UserModel update(@PathVariable int id, @Valid @RequestBody UserModel updated) {
        UserModel user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updated.getUsername());
        user.setPassword(updated.getPassword());
        user.setRoleId(updated.getRoleId());
        return repository.save(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}