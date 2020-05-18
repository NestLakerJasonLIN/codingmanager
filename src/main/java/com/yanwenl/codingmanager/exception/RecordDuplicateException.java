package com.yanwenl.codingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RecordDuplicateException extends RuntimeException {
    public RecordDuplicateException(String message) {
        super(message);
    }
}
