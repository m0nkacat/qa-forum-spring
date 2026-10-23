package org.example.pr4.service;

import org.example.pr4.model.QuestionsModel;
import java.util.List;

public interface QuestionsService {
    List<QuestionsModel> findAll();
    QuestionsModel findById(int id);
    List<QuestionsModel> search(String title);
    List<QuestionsModel> findPage(int page);
    QuestionsModel add(QuestionsModel question, String username);
    void delete(int id);
}