package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.*;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
                              defaultValue = "-1") int number,
                      @AuthenticationPrincipal User activeUser) {
        log.debug("GET with active user: " + activeUser);

        List<Record> records = getRecordsConditional(id, number, activeUser.getUsername());

        return doGet(records, model, activeUser.getUsername());
    }

    @PostMapping("/save")
    public @ResponseBody RecordFormValidationResponse save(
            @Valid @ModelAttribute("newRecord") Record record,
            BindingResult bindingResult,
            @AuthenticationPrincipal User activeUser) {

        RecordFormValidationResponse res = new RecordFormValidationResponse();

        if (bindingResult.hasErrors()) {
            log.debug("Error bind result for record");

            res.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            final List<RecordFormErrorMessage> errorMessages = new ArrayList<>();
            for (FieldError objectError : allErrors) {
                errorMessages.add(
                        new RecordFormErrorMessage(objectError.getField(),
                                "Please provide a valid " + objectError.getField().toLowerCase()));
            }
            log.debug("Add error: " + errorMessages);
            res.setErrorMessageList(errorMessages);
        } else {
            res.setStatus("SUCCESS");
            log.debug("Try to save record: " + record);

            recordService.add(record, activeUser.getUsername());
        }
        return res;
    }

    // TODO: need valid for record here
    @PostMapping("/update")
    public String update(
            @ModelAttribute("newRecord") Record record) {
        log.debug("Try to update record: " + record);

        recordService.update(record);

        return "redirect:/record";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("recordId") int id) {
        recordService.deleteById(id);

        return "redirect:/record";
    }

    // TODO: Currently only support search by label type
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam("keyword") String keyword,
                         @AuthenticationPrincipal User activeUser) {
        log.debug("Search by keyword: " + keyword);

        List<Label> labels = labelService.findByType(keyword);

        List<Record> records = new ArrayList<>();

        for (Label label : labels) {
            records.addAll(label.getRecords());
        }

        return doGet(records, model, activeUser.getUsername());
    }

    @PostMapping("/addLabel")
    public String addLabel(@ModelAttribute("recordLabelForm")
                                       RecordLabelForm recordLabelForm) {
        log.debug("Try to add label with tagForm: " + recordLabelForm);

        int recordId = recordLabelForm.getRecordId();

        Record record = recordService.findById(recordId);
        Label newLabel = labelService.findById(recordLabelForm.getNewLabelId());

        List<Label> labels = record.getLabels();
        labels.add(newLabel);
        record.setLabels(labels);

        recordService.update(record);

        return "redirect:/record";
    }

    // TODO: remove update label feature
    @PostMapping("/updateLabel")
    public String updateLabel(@ModelAttribute("recordLabelForm")
                                          RecordLabelForm recordLabelForm) {
        log.debug("Try to update label with form: " + recordLabelForm);

        int recordId = recordLabelForm.getRecordId();

        Record record = recordService.findById(recordId);
        Label newLabel = labelService.findById(recordLabelForm.getNewLabelId());

        int insertIdx = -1;

        for (int i=0; i<record.getLabels().size(); i++) {
            if (record.getLabels().get(i).getId() == recordLabelForm.getOldLabelId()) {
                insertIdx = i;
                break;
            }
        }

        record.getLabels().set(insertIdx, newLabel);

        recordService.update(record);

        return "redirect:/record";
    }

    @PostMapping("/deleteLabel")
    public String deleteLabel(@RequestParam("recordId") int recordId,
                         @RequestParam("labelId") int labelId) {
        log.debug("Try to delete tag with record Id and label Id: "
                + recordId + " : " + labelId);

        Record record = recordService.findById(recordId);
        int deleteIdx = -1;

        for (int i=0; i<record.getLabels().size(); i++) {
            if (record.getLabels().get(i).getId() == labelId) {
                deleteIdx = i;
                break;
            }
        }

        record.getLabels().remove(deleteIdx);

        recordService.update(record);

        return "redirect:/record";
    }

    private String doGet(List<Record> records, Model model, String userName) {
        model.addAttribute("records", records);

        model.addAttribute("labelsGroupByFieldGroupByRecord",
                recordService.groupLabelsByField(records));

        model.addAttribute("levelLabels", labelService.findByField("level"));

        Record newRecord = new Record();
        newRecord.setUserName(userName);
        model.addAttribute("newRecord", newRecord);

        model.addAttribute("recordLabelForm", new RecordLabelForm());

        model.addAttribute("labelByField",
                labelService.findByUserNameGroupByField(userName));

        return "list-records";
    }
}
