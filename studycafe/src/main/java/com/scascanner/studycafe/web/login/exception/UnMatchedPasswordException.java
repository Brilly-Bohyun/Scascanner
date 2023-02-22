package com.scascanner.studycafe.web.login.exception;

public class UnMatchedPasswordException extends RuntimeException{
    public UnMatchedPasswordException(String message){
        super(message);
    }
}
