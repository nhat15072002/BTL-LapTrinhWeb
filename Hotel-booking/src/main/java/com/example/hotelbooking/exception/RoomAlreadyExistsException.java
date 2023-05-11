package com.example.hotelbooking.exception;

public class RoomAlreadyExistsException extends RuntimeException{

    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}
