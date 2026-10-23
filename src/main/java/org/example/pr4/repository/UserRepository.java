package org.example.pr4.repository;

import org.example.pr4.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String username);
    List<UserModel> findByUsernameContainingIgnoreCase(String username);
}