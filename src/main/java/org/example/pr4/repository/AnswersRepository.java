package org.example.pr4.repository;

import org.example.pr4.model.AnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswersRepository extends JpaRepository<AnswersModel, Integer> {
    List<AnswersModel> findByBodyContaining(String keyword);
    List<AnswersModel> findByBodyContainingIgnoreCase(String keyword);
}