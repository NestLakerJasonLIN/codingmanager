package com.yanwenl.codingmanager.repository;

import com.yanwenl.codingmanager.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecordRepository extends JpaRepository<Record, Integer> {

    List<Record> findRecordByNumber(int number);
}
