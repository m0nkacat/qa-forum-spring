package org.example.pr4.repository;

import org.example.pr4.model.VotesModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotesRepository extends JpaRepository<VotesModel, Integer> {
    List<VotesModel> findByVoteType(Integer voteType);
    long countByQuestionQuestionIdAndVoteType(Integer questionId, Integer voteType);
    Optional<VotesModel> findByUserUsernameAndQuestionQuestionIdAndAnswerIsNull(String username, Integer questionId);
}
