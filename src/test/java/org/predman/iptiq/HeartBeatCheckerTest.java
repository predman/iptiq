package org.predman.iptiq;

import org.junit.jupiter.api.BeforeEach;


public class HeartBeatCheckerTest {
    
    private HeartBeatChecker checker;
    
    @BeforeEach
    public void beforeEach() {
        // NOTE: would typically use a mock with expectations or a stub here
        ProviderRegistry providerRegistry = new ProviderRegistry();
        for (int i = 0; i < 3; i++) {
            providerRegistry.register(new Provider());
        }
        checker = new HeartBeatChecker(providerRegistry);
    }
    
}
