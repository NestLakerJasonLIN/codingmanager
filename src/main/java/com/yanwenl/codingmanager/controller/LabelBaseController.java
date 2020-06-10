package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.service.LabelService;

import java.util.ArrayList;
import java.util.List;

public class LabelBaseController extends BaseController {
    LabelService labelService;

    public LabelBaseController(LabelService labelService) {
        this.labelService = labelService;
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
