package org.example.pr4.repository;

import org.example.pr4.model.QuestionTagsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagsRepository extends JpaRepository<QuestionTagsModel, Integer> {
}