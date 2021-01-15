package com.index.exception;

public class PresenceNotFoundException extends RuntimeException{
    public PresenceNotFoundException (long presenceId) {
        super("Could not find presence with id: " + presenceId);
    }
}
