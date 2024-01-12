package com.memberasso.fullstackbackend.exception;

public class FamilyNotFoundException extends RuntimeException {
    public FamilyNotFoundException(Long id) {
        super("Could not find the family with id " + id);
    }
}
