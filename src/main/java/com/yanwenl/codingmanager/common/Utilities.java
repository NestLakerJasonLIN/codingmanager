package com.yanwenl.codingmanager.common;

import com.yanwenl.codingmanager.model.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static Map<String, List<Label>> groupLabelsByField(List<Label> labels) {
        Map<String, List<Label>> labelByField = new HashMap<>();

        for (Label label : labels) {
            // Skip level labels
            if (label.getField().equals("level")) continue;

            labelByField.computeIfAbsent(label.getField(),
                    k -> new ArrayList<>()).add(label);
        }

        return labelByField;
    }
}
