package com.amitu.ratingservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7241590243798922301L;

	//extra properties that you want to mange
    public ResourceNotFoundException() {
        super("Resource not found on server !!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
