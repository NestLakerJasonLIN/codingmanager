package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/record")
@Slf4j
public class RecordController extends RecordBaseController {

    @Autowired
    public RecordController(RecordService theRecordService,
                            LabelService theLabelService) {
        super(theRecordService, theLabelService);
    }

    @GetMapping("")
    public String get(Model model,
                             @RequestParam(required = false,
                                     defaultValue = "-1") int id,
                             @RequestParam(required = false,
                                     defaultValue = "-1") int number) {
        List<Record> records = getRecordsConditional(id, number);
        model.addAttribute("records", records);
        return "list-records";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Record record = new Record();

        addLabelAttributesToModel(model);
        model.addAttribute("record", record);

        log.debug("Model: " + model);

        return "record-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(Model model, @RequestParam("recordId") int recordId) {
        Record record = recordService.findById(recordId);

        addLabelAttributesToModel(model);
        model.addAttribute("record", record);

        return "record-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("record") Record record) {
        log.debug("Try to save record: " + record);

        if (record.getId() == 0) {
            recordService.add(record);
        } else {
            recordService.update(record);
        }

        return "redirect:/record";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("recordId") int id) {
        recordService.deleteById(id);

        return "redirect:/record";
    }

    private void addLabelAttributesToModel(Model model) {
        List<String> fields = labelService.findDistinctFields();

        log.debug("Find distinct fields: " + fields);

        for (String field : fields) {
            List<Label> labelsByField = labelService.findByField(field);
            log.debug("Find labelsByField: " + labelsByField);
            model.addAttribute(field + "Label", labelsByField);
        }
    }
}
