package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RecordFormValidationResponse {
    private String status;
    private List<RecordFormErrorMessage> errorMessageList;

    public RecordFormValidationResponse() {
    }

    public RecordFormValidationResponse(String status, List<RecordFormErrorMessage> errorMessageList) {
        this.status = status;
        this.errorMessageList = errorMessageList;
    }
}
