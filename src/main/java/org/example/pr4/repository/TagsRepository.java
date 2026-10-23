package org.example.pr4.repository;

import org.example.pr4.model.TagsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TagsRepository extends JpaRepository<TagsModel, Integer> {
    Optional<TagsModel> findByName(String name);
    List<TagsModel> findByNameContainingIgnoreCase(String name);
}