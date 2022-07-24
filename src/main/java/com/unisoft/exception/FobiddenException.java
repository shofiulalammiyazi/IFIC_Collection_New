package com.unisoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class FobiddenException extends RuntimeException {

    public <T> FobiddenException(Class<T> tClass, String message) {
        super(message);
    }
}
