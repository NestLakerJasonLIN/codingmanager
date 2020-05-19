package com.yanwenl.codingmanager.controller;

import java.util.ArrayList;
import java.util.List;

import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Slf4j
public class RecordRestController extends RecordBaseController {

    @Autowired
    public RecordRestController(RecordService theRecordService) {
        super(theRecordService);
    }

    @GetMapping("/records")
    public List<Record> getRecords(
            @RequestParam(required = false, defaultValue = "-1") int id,
            @RequestParam(required = false, defaultValue = "-1") int number) {
        return getRecordsConditional(id, number);
    }

    @PostMapping("/records")
    public Record addRecord(@RequestBody Record theRecord) {
        recordService.add(theRecord);

        return theRecord;
    }

    @DeleteMapping("/records/{recordId}")
    public String deleteRecord(@PathVariable int recordId) {
        recordService.deleteById(recordId);
        return "Deleted record with number: " + recordId;
    }

    @PutMapping("/records")
    public Record updateRecord(@RequestBody Record record) {
        recordService.update(record);

        return recordService.findById(record.getId());
    }
}
