package org.example.pr4.service;

import org.example.pr4.model.TagsModel;
import java.util.List;

public interface TagsService {

    List<TagsModel> findAll();
    TagsModel findById(int id);
    List<TagsModel> search(String keyword);
    List<TagsModel> findPage(int page);

    TagsModel add(TagsModel obj);
    void delete(int id);
}