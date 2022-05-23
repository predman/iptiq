package org.predman.iptiq;

import java.util.List;


public class RoundRobinStrategy implements LoadBalancerStrategy {
    
    private final ProviderRegistry providerRegistry;
    private int counter;
    
    public RoundRobinStrategy(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
        counter = 0;
    }
    
    @Override
    public Provider nextProvider() {
        
        List<String> ids = providerRegistry.getActiveIds();
        int index = counter++ % ids.size();
        String id = ids.get(index);
        return providerRegistry.get(id);
    }
}
