package org.example.pr4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pr4.model.VotesModel;
import org.example.pr4.repository.VotesRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Votes", description = "Управление голосами")
@SecurityRequirement(name = "bearerAuth")
public class VotesApiController {

    @Autowired
    private VotesRepository votesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersRepository answersRepository;

    @GetMapping
    @Operation(summary = "Получить все голоса")
    public List<VotesModel> getAll() {
        return votesRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить голос по ID")
    public VotesModel getById(@PathVariable int id) {
        return votesRepository.findById(id).orElseThrow(() -> new RuntimeException("Vote not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать голос")
    public VotesModel create(@Valid @RequestBody VotesModel vote) {
        userRepository.findById(vote.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        questionsRepository.findById(vote.getQuestion().getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if (vote.getAnswer() != null && vote.getAnswer().getAnswerId() != null) {
            answersRepository.findById(vote.getAnswer().getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
        }
        return votesRepository.save(vote);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить голос")
    public VotesModel update(@PathVariable int id, @Valid @RequestBody VotesModel updated) {
        VotesModel v = votesRepository.findById(id).orElseThrow(() -> new RuntimeException("Vote not found"));
        v.setVoteType(updated.getVoteType());
        userRepository.findById(updated.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        questionsRepository.findById(updated.getQuestion().getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if (updated.getAnswer() != null && updated.getAnswer().getAnswerId() != null) {
            answersRepository.findById(updated.getAnswer().getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
        }
        v.setUser(updated.getUser());
        v.setQuestion(updated.getQuestion());
        v.setAnswer(updated.getAnswer());
        return votesRepository.save(v);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить голос")
    public void delete(@PathVariable int id) {
        votesRepository.deleteById(id);
    }
}