package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Label;

import java.util.List;

public interface LabelService {

    List<Label> findAll();

    Label findById(int id);

    List<String> findDistinctFields();

    List<Label> findByField(String field);

    void add(Label label);

    void update(Label label);

    void deleteById(int id);
}
