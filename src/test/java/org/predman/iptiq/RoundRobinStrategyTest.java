package org.predman.iptiq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RoundRobinStrategyTest {
    
    @Test
    public void next() {
        // setup a few providers
        Provider[] providers = new Provider[] {
                new Provider(),
                new Provider(),
                new Provider()
        };
        ProviderRegistry registry = new ProviderRegistry();
        for (int i = 0; i < providers.length; i++) {
            registry.register(providers[i]);
        }
        
        RoundRobinStrategy roundRobin = new RoundRobinStrategy(registry);
        
        Assertions.assertEquals(providers[0], roundRobin.nextProvider());
        Assertions.assertEquals(providers[1], roundRobin.nextProvider());
        Assertions.assertEquals(providers[2], roundRobin.nextProvider());
        Assertions.assertEquals(providers[0], roundRobin.nextProvider());
        Assertions.assertEquals(providers[1], roundRobin.nextProvider());
        Assertions.assertEquals(providers[2], roundRobin.nextProvider());
        
    }
}
