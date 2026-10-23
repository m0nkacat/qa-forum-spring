package org.example.pr4.service;

import org.example.pr4.model.QuestionTagsModel;
import java.util.List;

public interface QuestionTagsService {
    List<QuestionTagsModel> findAll();
    QuestionTagsModel findById(int id);
    List<QuestionTagsModel> findPage(int page);
    QuestionTagsModel add(String questionTitle, String tagName);
    void delete(int id);
}