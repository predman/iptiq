package org.predman.iptiq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RoundRobinStrategyTest {
    
    private RoundRobinStrategy strategy;
    
    @BeforeEach
    public void beforeEach() {
        // NOTE: would typically use a mock / stub here
        ProviderRegistry providerRegistry = new ProviderRegistry();
        for (int i = 0; i < 3; i++) {
            providerRegistry.register(new Provider());
        }
        strategy = new RoundRobinStrategy(providerRegistry);
    }
    
    @Test
    public void next() {
        Assertions.assertNotNull(strategy.nextProvider());
    }
}
