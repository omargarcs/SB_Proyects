package com.ogarcs.hotel.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException() {
        super("Recurso no encontrado en el Servidor !!!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
