package org.example.pr4.repository;

import org.example.pr4.model.QuestionTagsApiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagsApiRepository extends JpaRepository<QuestionTagsApiModel, Integer> {
}