package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LabelBaseController extends BaseController {
    LabelService labelService;

    public LabelBaseController(LabelService labelService) {
        this.labelService = labelService;
    }

    List<Label> getLabelsConditional(int id, String field) {
        List<Label> labels = new ArrayList<>();

        if (id < 0 && field.isEmpty()) {
            labels = labelService.findAll();
        } else if (id > 0) {
            labels.add(labelService.findById(id));
        } else {
            labels = labelService.findByField(field);
        }

        return labels;
    }
}
