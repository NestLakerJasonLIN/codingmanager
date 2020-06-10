package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecordLabelForm {
    private int oldLabelId;
    private int newLabelId;
    private int recordId;
}
