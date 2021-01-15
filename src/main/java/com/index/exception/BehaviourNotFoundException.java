package com.index.exception;

public class BehaviourNotFoundException extends RuntimeException{
    public BehaviourNotFoundException (long behaviourId) {
        super("Could not find behaviour with id: " + behaviourId);
    }
}
