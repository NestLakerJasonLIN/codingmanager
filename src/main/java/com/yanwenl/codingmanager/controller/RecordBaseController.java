package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RecordBaseController {

    @Value("${info.app.name}")
    String appName;

    RecordService recordService;

    public RecordBaseController(RecordService theRecordService) {
        recordService = theRecordService;
    }

    List<Record> getRecordsConditional(int id, int number) {
        log.debug("getRecordsConditional: " +
                "id: " + id + " number: " + number);

        if (id < 0 && number < 0) {
            return recordService.findAll();
        } else if (id > 0) {
            List<Record> ret = new ArrayList<>();
            ret.add(recordService.findById(id));
            return ret;
        } else {
            return recordService.findByNumber(number);
        }
    }
}
