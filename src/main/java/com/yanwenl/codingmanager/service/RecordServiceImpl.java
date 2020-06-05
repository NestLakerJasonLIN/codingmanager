package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.exception.InvalidParametersException;
import com.yanwenl.codingmanager.exception.ResourceNotFoundException;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository theRecordRepository) {
        recordRepository = theRecordRepository;
    }

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public List<Record> findByUserName(String userName) {
        return recordRepository.findRecordByUserName(userName);
    }

    @Override
    public Record findById(int id) {
        Optional<Record> result = recordRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "Record id not found: " + id);
        } else {
            Record record = result.get();
            return record;
        }
    }

    @Override
    public List<Record> findByNumber(int number) {
        return recordRepository.findRecordByNumber(number);
    }

    // Currently allow duplicate add
    @Override
    public void add(Record record, String userName) {
        // Skip existed record with same number
        int number = record.getNumber();

        List<Record> existedRecords = recordRepository.findRecordByNumber(number);

        if (existedRecords != null && existedRecords.size() > 0) {
            for (Record existedRecord : existedRecords) {
                if (existedRecord.getUserName().equals(userName)) {
                    log.warn("Record already existed (do not add): " + record);
                    return;
                }
            }
        }

        log.info("Record is added: " + record);

        record.setId(0);

        recordRepository.save(record);
    }

    @Override
    public void update(Record record) {
        // ID field existed should not be zero in update
        int id = record.getId();
        if (id == 0) {
            throw new InvalidParametersException(
                    "Record id 0 not allowed in update request");
        }

        Optional<Record> result = recordRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "Record id not found (do not update): "
                            + id);
        } else {
            log.debug("Record is updated: " + id);
            recordRepository.save(record);
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<Record> result = recordRepository.findById(id);

        if (result.isPresent()) {
            Record record = result.get();
            log.info("Record is deleted: " + record);
            recordRepository.delete(record);
        } else {
            throw new ResourceNotFoundException(
                    "Record id not found: " + id);
        }
    }
}
