package org.predman.iptiq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ProviderTest {
    
    @Test
    public void get_notnull() {
        Provider provider = new Provider();
        Assertions.assertNotNull(provider.get(), "provider ID should not be null");
    }
    
    @Test
    public void get_uniqueid() {
        Provider p1 = new Provider();
        Provider p2 = new Provider();
        Assertions.assertNotEquals(p1, p2, "provider ID must be unique");
    }
}
