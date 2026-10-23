package org.example.pr4.service.impl;

import org.example.pr4.model.QuestionsModel;
import org.example.pr4.model.UserModel;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<QuestionsModel> findAll() {
        return questionsRepository.findAll();
    }

    @Override
    public QuestionsModel findById(int id) {
        return questionsRepository.findById(id).orElse(null);
    }

    @Override
    public List<QuestionsModel> search(String title) {
        return questionsRepository.findByTitleContaining(title);
    }

    @Override
    public List<QuestionsModel> findPage(int page) {
        return questionsRepository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public QuestionsModel add(QuestionsModel question, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        question.setUser(user);
        return questionsRepository.save(question);
    }

    @Override
    public void delete(int id) {
        questionsRepository.deleteById(id);
    }
}