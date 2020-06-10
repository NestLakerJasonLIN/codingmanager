package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Label;

import java.util.List;
import java.util.Map;

public interface LabelService {

    List<Label> findByUserName(String userName);

    Label findById(int id);

    Map<String, List<Label>> findByUserNameGroupByField(String userName);

    List<Label> findByField(String field);

    List<Label> findByType(String type);

    void add(Label label);

    void update(Label label);

    void deleteById(int id);
}
