package org.example.pr4.service.impl;

import org.example.pr4.model.RolesModel;
import org.example.pr4.repository.RolesRepository;
import org.example.pr4.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository repository;

    @Override
    public List<RolesModel> findAll() { return repository.findAll(); }

    @Override
    public RolesModel findById(int id) { return repository.findById(id).orElse(null); }

    @Override
    public List<RolesModel> search(String keyword) { return repository.findByNameContainingIgnoreCase(keyword); }

    @Override
    public List<RolesModel> findPage(int page) { return repository.findAll(PageRequest.of(page - 1, 10)).getContent(); }

    @Override
    public RolesModel add(RolesModel model) { return repository.save(model); }

    @Override
    public void delete(int id) { repository.deleteById(id); }
}