package org.predman.iptiq;

import java.util.UUID;


public class Provider {
    
    private final String identifier;
    
    public Provider() {
        identifier = UUID.randomUUID().toString();
    }
    
    public String get() {
        return identifier;
    }
}
