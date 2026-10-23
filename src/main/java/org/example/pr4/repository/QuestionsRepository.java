package org.example.pr4.repository;

import org.example.pr4.model.QuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends JpaRepository<QuestionsModel, Integer> {
    Optional<QuestionsModel> findByTitle(String title);
    List<QuestionsModel> findByTitleContaining(String keyword);
}