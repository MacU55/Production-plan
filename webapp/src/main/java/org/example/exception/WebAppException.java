package org.example.exception;

public class WebAppException extends RuntimeException {
    public WebAppException(ServiceException e) {
        super(e);
    }
}
