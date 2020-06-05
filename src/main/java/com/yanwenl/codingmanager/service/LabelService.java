package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Label;

import java.util.List;

public interface LabelService {

    List<Label> findAll();

    List<Label> findByUserName(String userName);

    Label findById(int id);

    List<String> findDistinctFields();

    List<Label> findByField(String field);

    List<Label> findByField(String field, String userName);

    List<Label> findByType(String type);

    void add(Label label);

    void update(Label label);

    void deleteById(int id);
}
