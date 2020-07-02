package com.index.exceptions;

public class SpringGradebookException extends RuntimeException {
    public SpringGradebookException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringGradebookException(String exMessage) {

        super(exMessage);
    }
}
