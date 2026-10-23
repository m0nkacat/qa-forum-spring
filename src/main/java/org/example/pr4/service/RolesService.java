package org.example.pr4.service;

import org.example.pr4.model.RolesModel;
import java.util.List;

public interface RolesService {

    List<RolesModel> findAll();
    RolesModel findById(int id);
    List<RolesModel> search(String keyword);
    List<RolesModel> findPage(int page);

    RolesModel add(RolesModel obj);
    void delete(int id);
}