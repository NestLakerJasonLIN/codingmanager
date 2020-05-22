package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.model.Record;
import com.yanwenl.codingmanager.model.Tag;
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
    public String save(@ModelAttribute("tag") Tag tag) {
        log.debug("Try to save tag: " + tag);

        if (tag.getId() == 0) {
            tagService.add(tag);
        } else {
            tagService.update(tag);
        }

        return "redirect:/record";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("tagId") int id) {
        tagService.deleteById(id);

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
