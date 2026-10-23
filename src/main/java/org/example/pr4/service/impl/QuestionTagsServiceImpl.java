package org.example.pr4.service.impl;

import org.example.pr4.model.QuestionTagsModel;
import org.example.pr4.model.QuestionsModel;
import org.example.pr4.model.TagsModel;
import org.example.pr4.repository.QuestionTagsRepository;
import org.example.pr4.repository.QuestionsRepository;
import org.example.pr4.repository.TagsRepository;
import org.example.pr4.service.QuestionTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionTagsServiceImpl implements QuestionTagsService {

    @Autowired
    private QuestionTagsRepository questionTagsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public List<QuestionTagsModel> findAll() {
        return questionTagsRepository.findAll();
    }

    @Override
    public QuestionTagsModel findById(int id) {
        return questionTagsRepository.findById(id).orElse(null);
    }

    @Override
    public List<QuestionTagsModel> findPage(int page) {
        return questionTagsRepository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public QuestionTagsModel add(String questionTitle, String tagName) {
        QuestionsModel question = questionsRepository.findByTitle(questionTitle)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        TagsModel tag = tagsRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Тег не найден"));
        QuestionTagsModel qt = new QuestionTagsModel();
        qt.setQuestion(question);
        qt.setTag(tag);
        return questionTagsRepository.save(qt);
    }

    @Override
    public void delete(int id) {
        questionTagsRepository.deleteById(id);
    }
}