package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Tag;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.RecordService;
import com.yanwenl.codingmanager.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TagBaseController extends BaseController {
    TagService tagService;
    RecordService recordService;
    LabelService labelService;

    // TODO: check if autowired in parent class feasible
    @Autowired
    public TagBaseController(TagService tagService,
                             RecordService recordService,
                             LabelService labelService) {
        this.tagService = tagService;
        this.recordService = recordService;
        this.labelService = labelService;
    }

    // TODO: a better to reuse those codes
    public List<Tag> getTagsConditional(int id) {
        List<Tag> tags = new ArrayList<>();

        if (id < 0) {
            tags = tagService.findAll();
        } else {
            tags.add(tagService.findById(id));
        }

        return tags;
    }
}
