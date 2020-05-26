package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.model.Tag;
import com.yanwenl.codingmanager.model.TagForm;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import com.yanwenl.codingmanager.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tag")
@Slf4j
public class TagController extends TagBaseController {

    public TagController(TagService tagService,
                         RecordService recordService,
                         LabelService labelService) {
        super(tagService, recordService, labelService);
    }

    @GetMapping("")
    public String get(Model model,
                      @RequestParam(required = false,
                              defaultValue = "-1") int id) {
        List<Tag> tags = getTagsConditional(id);

        model.addAttribute("tags", tags);

        return "list-tags";
    }

    @PostMapping("/showFormForAdd")
    public String showFormForAdd(Model model,
                                 @RequestParam("recordId") int recordId) {
        log.debug("showFormForAdd recordId: " + recordId);

        Tag tag = new Tag();
        tag.setRecordId(recordId);

        model.addAttribute("tag", tag);
        addLabelAttributesToModel(model);

        log.debug("Model: " + model);

        return "tag-new-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(Model model,
                                    @RequestParam("recordId") int recordId,
                                    @RequestParam("labelId") int labelId) {
        Tag tag = tagService.findByRecordIdAndLabelId(recordId, labelId);
        Record record = recordService.findById(recordId);
        Label label = labelService.findById(labelId);
        List<Label> labelByField = labelService.findByField(label.getField());

        model.addAttribute("tag", tag);
        model.addAttribute("record", record);
        model.addAttribute("label", label);
        model.addAttribute("labelByField", labelByField);

        return "tag-update-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("tagForm") TagForm tagForm) {
        log.debug("Try to save tagForm: " + tagForm);

        int recordId = tagForm.getRecordId();
        int oldLabelId = tagForm.getOldLabelId();
        int newLabelId = tagForm.getNewLabelId();

        if (oldLabelId == 0) {
            Tag tag = new Tag();
            tag.setRecordId(recordId);
            tag.setLabelId(newLabelId);
            tagService.add(tag);
        } else {
            Tag oldTag = tagService.findByRecordIdAndLabelId(recordId, oldLabelId);
            oldTag.setLabelId(newLabelId);
            tagService.update(oldTag);
        }

        return "redirect:/record";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("recordId") int recordId,
                         @RequestParam("labelId") int labelId) {
        log.debug("Try to delete tag with record Id and label Id: "
                + recordId + " : " + labelId);

        tagService.deleteByRecordIdAndLabelId(recordId, labelId);

        return "redirect:/record";
    }

    private void addLabelAttributesToModel(Model model) {
        List<String> fields = labelService.findDistinctFields();
        Map<String, List<Label>> labelByField = new HashMap<>();

        for (String field : fields) {
            // Skip level labels
            if (field.equals("level")) continue;

            List<Label> list = labelService.findByField(field);
            labelByField.put(field, list);
        }

        log.debug("Find labelByField: " + labelByField);

        model.addAttribute("labelByField", labelByField);
    }
}
