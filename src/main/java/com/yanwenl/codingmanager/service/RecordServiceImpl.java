package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.common.Utilities;
import com.yanwenl.codingmanager.exception.InvalidParametersException;
import com.yanwenl.codingmanager.exception.ResourceNotFoundException;
import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<Record, Map<String, List<Label>>>
            groupLabelsByField(List<Record> records) {
        Map<Record, Map<String, List<Label>>>
                labelsGroupByFieldGroupByRecord = new HashMap<>();

        for (Record record : records) {
            labelsGroupByFieldGroupByRecord.put(record, new HashMap<>());
            Map<String, List<Label>> labelByField
                    = Utilities.groupLabelsByField(record.getLabels());
            labelsGroupByFieldGroupByRecord.put(record, labelByField);
        }

        return labelsGroupByFieldGroupByRecord;
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
            Record existedRecord = result.get();
            record.setLabels(existedRecord.getLabels());
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
