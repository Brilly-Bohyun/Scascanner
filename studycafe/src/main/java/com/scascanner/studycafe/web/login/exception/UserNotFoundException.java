package com.scascanner.studycafe.web.login.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message, Throwable t){
        super(message,t);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(){
        super();
    }
}
