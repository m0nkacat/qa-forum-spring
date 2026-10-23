package org.example.pr4.repository;

import org.example.pr4.model.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<RolesModel, Integer> {
    Optional<RolesModel> findByName(String name);
    List<RolesModel> findByNameContainingIgnoreCase(String name);
}