package org.predman.iptiq;

import java.util.UUID;


public class Provider implements Checkable {
    
    private final String identifier;
    
    public Provider() {
        identifier = UUID.randomUUID().toString();
    }
    
    public String get() {
        return identifier;
    }
    
    /**
     * @return True if alive, False otherwise
     */
    @Override
    public boolean check() {
        return true;
    }
}
