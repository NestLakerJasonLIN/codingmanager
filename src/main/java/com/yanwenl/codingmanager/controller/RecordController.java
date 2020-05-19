package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class RecordController extends RecordBaseController {

    @Autowired
    public RecordController(RecordService theRecordService) {
        super(theRecordService);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", appName);
        return "index";
    }

    @GetMapping("/records")
    public String getRecords(Model model,
                             @RequestParam(required = false,
                                     defaultValue = "-1") int id,
                             @RequestParam(required = false,
                                     defaultValue = "-1") int number) {
        List<Record> records = getRecordsConditional(id, number);
        model.addAttribute("records", records);
        return "list-records";
    }

    @GetMapping("/records/showFormForAdd")
    public String showForForAdd(Model model) {
        Record record = new Record();

        model.addAttribute("record", record);

        return "record-form";
    }

    @PostMapping("/records/showFormForUpdate")
    public String showFormForUpdate(Model model, @RequestParam("recordId") int recordId) {
        Record record = recordService.findById(recordId);

        model.addAttribute("record", record);

        return "record-form";
    }

    @PostMapping("/records/save")
    public String saveRecords(@ModelAttribute("record") Record record) {
        log.debug("Try to save record: " + record);

        if (record.getId() == 0) {
            recordService.add(record);
        } else {
            recordService.update(record);
        }

        return "redirect:/records";
    }

    @PostMapping("/records/delete")
    public String delete(@RequestParam("recordId") int id) {
        recordService.deleteById(id);

        return "redirect:/records";
    }
}
