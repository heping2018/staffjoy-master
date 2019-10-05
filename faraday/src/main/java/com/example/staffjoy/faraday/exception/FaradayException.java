package com.example.staffjoy.faraday.exception;

public class FaradayException extends RuntimeException {
    public FaradayException(String message){
        super(message);
    }
    public FaradayException(String message, Throwable throwable){
        super(message,throwable);
    }
}
