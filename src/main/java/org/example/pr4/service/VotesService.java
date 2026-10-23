package org.example.pr4.service;

import org.example.pr4.model.VotesModel;
import java.util.List;

public interface VotesService {
    List<VotesModel> findAll();
    VotesModel findById(int id);
    List<VotesModel> findPage(int page);
    List<VotesModel> search(Integer voteType);
    VotesModel add(VotesModel vote, String username, String questionTitle, Integer answerId);
    long countByQuestionAndType(int questionId, int voteType);
    VotesModel findMyQuestionVote(int questionId);
    void voteQuestion(int questionId, String username, int voteType);
    void delete(int id);
}