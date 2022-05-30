package com.company.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String attach_not_found) {
        super(attach_not_found);
    }
}
