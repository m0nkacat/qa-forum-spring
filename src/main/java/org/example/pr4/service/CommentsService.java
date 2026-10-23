package org.example.pr4.service;

import org.example.pr4.model.CommentsModel;
import java.util.List;

public interface CommentsService {
    List<CommentsModel> findAll();
    CommentsModel findById(int id);
    List<CommentsModel> search(String keyword);
    List<CommentsModel> findPage(int page);
    CommentsModel add(CommentsModel comment, String username, String questionTitle, Integer answerId);
    void delete(int id);
}