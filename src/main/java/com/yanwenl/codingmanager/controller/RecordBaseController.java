package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RecordBaseController extends BaseController {

    RecordService recordService;
    LabelService labelService;

    public RecordBaseController(RecordService theRecordService,
                                LabelService theLabelService) {
        recordService = theRecordService;
        labelService = theLabelService;
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
