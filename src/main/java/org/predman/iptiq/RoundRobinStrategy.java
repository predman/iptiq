package org.predman.iptiq;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class RoundRobinStrategy implements LoadBalancerStrategy {
    
    private final ProviderRegistry providerRegistry;
    private AtomicInteger counter;
    
    public RoundRobinStrategy(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
        counter = new AtomicInteger(0);
    }
    
    @Override
    public Provider nextProvider() {
        
        List<String> ids = providerRegistry.getActiveIds();
        int index = counter.getAndIncrement() % ids.size();
        String id = ids.get(index);
        return providerRegistry.get(id);
    }
}
