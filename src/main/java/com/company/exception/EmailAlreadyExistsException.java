package com.company.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email_already_exits) {
        super(email_already_exits);
    }
}
