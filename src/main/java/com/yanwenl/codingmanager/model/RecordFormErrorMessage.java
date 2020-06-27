package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecordFormErrorMessage {
    private String fieldName;
    private String message;

    public RecordFormErrorMessage() {
    }

    public RecordFormErrorMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
