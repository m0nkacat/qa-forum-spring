package org.example.pr4.service.impl;

import org.example.pr4.model.CommentsModel;
import org.example.pr4.model.UserModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.model.AnswersModel;
import org.example.pr4.repository.CommentsRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.AnswersRepository;
import org.example.pr4.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Override
    public List<CommentsModel> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public CommentsModel findById(int id) {
        return commentsRepository.findById(id).orElse(null);
    }

    @Override
    public List<CommentsModel> search(String keyword) {
        return commentsRepository.findByBodyContaining(keyword);
    }

    @Override
    public List<CommentsModel> findPage(int page) {
        return commentsRepository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public CommentsModel add(CommentsModel comment, String username, String questionTitle, Integer answerId) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        comment.setUser(user);
        if (questionTitle != null && !questionTitle.isEmpty()) {
            QuestionsModel question = questionsRepository.findByTitle(questionTitle)
                    .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
            comment.setQuestion(question);
        }
        if (answerId != null) {
            AnswersModel answer = answersRepository.findById(answerId)
                    .orElseThrow(() -> new RuntimeException("Ответ не найден"));
            comment.setAnswer(answer);
        }
        return commentsRepository.save(comment);
    }

    @Override
    public void delete(int id) {
        commentsRepository.deleteById(id);
    }
}