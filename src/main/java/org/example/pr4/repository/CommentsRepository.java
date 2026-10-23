package org.example.pr4.repository;

import org.example.pr4.model.CommentsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsModel, Integer> {
    List<CommentsModel> findByBodyContaining(String keyword);
    List<CommentsModel> findByBodyContainingIgnoreCase(String keyword);
}