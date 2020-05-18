package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Record;

import java.util.List;


public interface RecordService {
    List<Record> findAll();

    Record findById(int id);

    List<Record> findByNumber(int number);

    void add(Record record);

    void update(Record record);

    void deleteById(int id);
}
