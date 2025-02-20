package com.amitu.hotelservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 9037636248831139765L;

	public ResourceNotFoundException(String s) {
        super(s);
    }

    public ResourceNotFoundException(){
        super("Resource not found !!");
    }
}
