package com.company.exception;

public class AppForbiddenException extends RuntimeException {
    public AppForbiddenException(String s) {
        super(s);
    }
}
