package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/label")
@Slf4j
public class LabelController extends LabelBaseController {

    @Autowired
    public LabelController(LabelService labelService) {
        super(labelService);
    }

    @GetMapping("")
    public String get(Model model,
                      @RequestParam(required = false,
                                     defaultValue = "-1") int id,
                      @RequestParam(required = false,
                                     defaultValue = "") String field,
                      @AuthenticationPrincipal User activeUser) {
        List<Label> labels = getLabelsConditional(id, field, activeUser.getUsername());
        model.addAttribute("labels", labels);
        return "list-labels";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Label label = new Label();

        model.addAttribute("label", label);

        log.debug("Model: " + model);

        return "label-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(Model model, @RequestParam("labelId") int labelId) {
        Label label = labelService.findById(labelId);

        model.addAttribute("label", label);

        return "label-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("label") Label label,
                       @AuthenticationPrincipal User activeUser) {
        label.setUserName(activeUser.getUsername());

        log.debug("Try to save label: " + label);

        // TODO: case check

        if (label.getId() == 0) {
            labelService.add(label);
        } else {
            labelService.update(label);
        }

        return "redirect:/label";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("labelId") int id) {
        labelService.deleteById(id);

        return "redirect:/label";
    }
}
