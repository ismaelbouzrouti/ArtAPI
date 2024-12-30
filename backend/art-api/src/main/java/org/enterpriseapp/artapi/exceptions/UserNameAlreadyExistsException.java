package org.enterpriseapp.artapi.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException{

    public UserNameAlreadyExistsException(String message){
        super(message);
    }
}
