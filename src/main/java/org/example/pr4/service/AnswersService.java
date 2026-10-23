package org.example.pr4.service;

import org.example.pr4.model.AnswersModel;
import java.util.List;

public interface AnswersService {
    List<AnswersModel> findAll();
    AnswersModel findById(int id);
    List<AnswersModel> search(String keyword);
    List<AnswersModel> findPage(int page);

    AnswersModel add(AnswersModel model, String questionTitle);
    AnswersModel addToQuestion(AnswersModel model, int questionId);
    void delete(int id);
}