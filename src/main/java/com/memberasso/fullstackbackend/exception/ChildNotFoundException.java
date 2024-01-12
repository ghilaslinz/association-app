package com.memberasso.fullstackbackend.exception;

public class ChildNotFoundException extends RuntimeException {
    public ChildNotFoundException(Long id) {
        super("Could not find child with id: " + id);
    }
}
