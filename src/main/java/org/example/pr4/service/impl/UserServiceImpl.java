package org.example.pr4.service.impl;

import org.example.pr4.model.UserModel;
import org.example.pr4.repository.UserRepository;
import org.example.pr4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<UserModel> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UserModel> findPage(int page) {
        return repository.findAll(PageRequest.of(page - 1, 10)).getContent();
    }

    @Override
    public UserModel findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<UserModel> search(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public void add(UserModel user) {
        repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}