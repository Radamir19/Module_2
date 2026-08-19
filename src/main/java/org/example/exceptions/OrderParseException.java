package org.example.exceptions;

public class OrderParseException extends RuntimeException{

    public OrderParseException(String message){
        super(message);
    }
}
