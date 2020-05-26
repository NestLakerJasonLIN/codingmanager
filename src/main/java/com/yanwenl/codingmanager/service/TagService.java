package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    List<Tag> findAll();

    Tag findById(int id);

    List<Tag> findByRecordId(int rid);

    List<Tag> findByLabelId(int lid);

    Tag findByRecordIdAndLabelId(int rid, int lid);

    void add(Tag tag);

    void update(Tag tag);

    void deleteById(int id);

    void deleteByRecordId(int rid);

    void deleteByLabelId(int lid);

    void deleteByRecordIdAndLabelId(int rid, int lid);

    Map<Integer, List<Integer>> findLabelGroupByRecord();
}
