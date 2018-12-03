package com.babelway.interview.api.callers;

/**
 * Copyright Babelway 2017.
 */
public class ApiCallException extends RuntimeException {
    public ApiCallException(String message, Exception cause) {
        super(message,cause);
    }
}
