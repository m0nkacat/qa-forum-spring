package org.example.pr4.service;

import org.example.pr4.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> findAll();
    List<UserModel> findPage(int page);
    UserModel findById(int id);
    List<UserModel> search(String username);
    void add(UserModel user);
    void delete(int id);
}