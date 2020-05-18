package com.yanwenl.codingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordExistedException extends RuntimeException {
    public RecordExistedException(String message) {
        super(message);
    }
}
