package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.service.LabelService;
import com.yanwenl.codingmanager.service.TagService;

import java.util.ArrayList;
import java.util.List;

public class LabelBaseController extends BaseController {
    LabelService labelService;
    TagService tagService;

    public LabelBaseController(LabelService labelService,
                               TagService tagService) {
        this.labelService = labelService;
        this.tagService = tagService;
    }

    List<Label> getLabelsConditional(int id, String field, String userName) {
        List<Label> labels = new ArrayList<>();

        if (id < 0 && field.isEmpty()) {
            labels = labelService.findByUserName(userName);
        } else if (id > 0) {
            labels.add(labelService.findById(id));
        } else {
            labels = labelService.findByField(field);
        }

        return labels;
    }
}
