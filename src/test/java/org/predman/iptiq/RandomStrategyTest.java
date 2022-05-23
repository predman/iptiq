package org.predman.iptiq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RandomStrategyTest {
    
    private RandomStrategy randomStrategy;
    
    @BeforeEach
    public void beforeEach() {
        // NOTE: would typically use a mock / stub here
        ProviderRegistry providerRegistry = new ProviderRegistry();
        for (int i = 0; i < 3; i++) {
            providerRegistry.register(new Provider());
        }
        randomStrategy = new RandomStrategy(providerRegistry);
    }
    
    @Test
    public void next() {
        Assertions.assertNotNull(randomStrategy.nextProvider());
    }
}
