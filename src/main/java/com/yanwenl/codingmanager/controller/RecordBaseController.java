package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import com.yanwenl.codingmanager.service.TagService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class RecordBaseController extends BaseController {

    RecordService recordService;
    LabelService labelService;
    TagService tagService;

    public RecordBaseController(RecordService theRecordService,
                                LabelService theLabelService,
                                TagService theTagService) {
        recordService = theRecordService;
        labelService = theLabelService;
        tagService = theTagService;
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

    Map<Record, Map<String, List<Label>>> findLabelGroupByRecord() {
        Map<Integer, List<Integer>> labelIdGroupByRecordId
                = tagService.findLabelGroupByRecord();
        Map<Record, Map<String, List<Label>>>
                labelGroupByFieldGroupByRecord = new HashMap<>();

        for (Integer rid : labelIdGroupByRecordId.keySet()) {
            Record record = recordService.findById(rid);
            labelGroupByFieldGroupByRecord.put(record, new HashMap<>());
            Map<String, List<Label>> labelByField
                    = groupLabelByField(labelIdGroupByRecordId.get(rid));
            labelGroupByFieldGroupByRecord.put(record, labelByField);
        }

        return labelGroupByFieldGroupByRecord;
    }

    Map<String, List<Label>> groupLabelByField(List<Integer> labelIds) {
        Map<String, List<Label>> labelByField = new HashMap<>();

        for (Integer labelId : labelIds) {
            Label label = labelService.findById(labelId);
            labelByField.computeIfAbsent(label.getField(),
                    k -> new ArrayList<>()).add(label);
        }

        return labelByField;
    }
}
