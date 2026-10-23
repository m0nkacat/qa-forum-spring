package org.example.pr4.service.impl;

import org.example.pr4.model.AnswersModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.repository.AnswersRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.service.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnswersServiceImpl implements AnswersService {

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Override
    public List<AnswersModel> findAll() {
        return answersRepository.findAll();
    }

    @Override
    public List<AnswersModel> findPage(int page) {
        return answersRepository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public AnswersModel findById(int id) {
        return answersRepository.findById(id).orElse(null);
    }

    @Override
    public List<AnswersModel> search(String keyword) {
        return answersRepository.findByBodyContaining(keyword);
    }

    @Override
    public AnswersModel add(AnswersModel model, String questionTitle) {
        QuestionsModel question = questionsRepository.findByTitle(questionTitle)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        model.setQuestion(question);
        return answersRepository.save(model);
    }

    @Override
    public AnswersModel addToQuestion(AnswersModel model, int questionId) {
        QuestionsModel question = questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        model.setQuestion(question);
        return answersRepository.save(model);
    }

    @Override
    public void delete(int id) {
        answersRepository.deleteById(id);
    }
}