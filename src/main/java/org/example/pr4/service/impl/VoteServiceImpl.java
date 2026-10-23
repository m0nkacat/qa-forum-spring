package org.example.pr4.service.impl;

import org.example.pr4.model.VotesModel;
import org.example.pr4.model.UserModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.model.AnswersModel;
import org.example.pr4.repository.VotesRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.AnswersRepository;
import org.example.pr4.service.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoteServiceImpl implements VotesService {

    @Autowired
    private VotesRepository votesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Override
    public List<VotesModel> findAll() {
        return votesRepository.findAll();
    }

    @Override
    public VotesModel findById(int id) {
        return votesRepository.findById(id).orElse(null);
    }

    @Override
    public List<VotesModel> findPage(int page) {
        return votesRepository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public List<VotesModel> search(Integer voteType) {
        return votesRepository.findByVoteType(voteType);
    }

    @Override
    public VotesModel add(VotesModel vote, String username, String questionTitle, Integer answerId) {
        UserModel user = getOrCreateUser(username);
        vote.setUser(user);
        QuestionsModel question = questionsRepository.findByTitle(questionTitle)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        vote.setQuestion(question);
        if (answerId != null) {
            AnswersModel answer = answersRepository.findById(answerId)
                    .orElseThrow(() -> new RuntimeException("Ответ не найден"));
            vote.setAnswer(answer);
        }
        return votesRepository.save(vote);
    }

    @Override
    public long countByQuestionAndType(int questionId, int voteType) {
        return votesRepository.countByQuestionQuestionIdAndVoteType(questionId, voteType);
    }

    @Override
    public VotesModel findMyQuestionVote(int questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return null;
        }
        return votesRepository
                .findByUserUsernameAndQuestionQuestionIdAndAnswerIsNull(auth.getName(), questionId)
                .orElse(null);
    }

    @Override
    public void voteQuestion(int questionId, String username, int voteType) {
        UserModel user = getOrCreateUser(username);
        QuestionsModel question = questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));

        VotesModel vote = votesRepository
                .findByUserUsernameAndQuestionQuestionIdAndAnswerIsNull(username, questionId)
                .orElse(null);

        if (vote == null) {
            vote = new VotesModel();
            vote.setUser(user);
            vote.setQuestion(question);
            vote.setVoteType(voteType);
            votesRepository.save(vote);
        } else if (vote.getVoteType().equals(voteType)) {
            votesRepository.delete(vote);
        } else {
            vote.setVoteType(voteType);
            votesRepository.save(vote);
        }
    }

    private UserModel getOrCreateUser(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            UserModel user = new UserModel();
            user.setUsername(username);
            user.setPassword("*");
            user.setRoleId(1);
            return userRepository.save(user);
        });
    }

    @Override
    public void delete(int id) {
        votesRepository.deleteById(id);
    }
}