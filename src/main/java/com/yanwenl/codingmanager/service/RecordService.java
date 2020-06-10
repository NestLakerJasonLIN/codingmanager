package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;

import java.util.List;
import java.util.Map;


public interface RecordService {
    List<Record> findByUserName(String userName);

    Record findById(int id);

    Map<Record, Map<String, List<Label>>>
        groupLabelsByField(List<Record> records);

    List<Record> findByNumber(int number);

    void add(Record record, String userName);

    void update(Record record);

    void deleteById(int id);
}
