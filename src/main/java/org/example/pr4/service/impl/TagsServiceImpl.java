package org.example.pr4.service.impl;

import org.example.pr4.model.TagsModel;
import org.example.pr4.repository.TagsRepository;
import org.example.pr4.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository repository;

    @Override
    public List<TagsModel> findAll() { return repository.findAll(); }

    @Override
    public TagsModel findById(int id) { return repository.findById(id).orElse(null); }

    @Override
    public List<TagsModel> search(String name) { return repository.findByNameContainingIgnoreCase(name); }

    @Override
    public List<TagsModel> findPage(int page) { return repository.findAll(PageRequest.of(page - 1, 10)).getContent(); }

    @Override
    public TagsModel add(TagsModel model) { return repository.save(model); }

    @Override
    public void delete(int id) { repository.deleteById(id); }
}