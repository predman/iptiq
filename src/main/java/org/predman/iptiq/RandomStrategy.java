package org.predman.iptiq;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class RandomStrategy implements LoadBalancerStrategy {
    
    private ProviderRegistry providerRegistry;
    
    public RandomStrategy(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }
    
    @Override
    public Provider nextProvider() {
        
        List<String> ids = providerRegistry.getIds();
        int index = ThreadLocalRandom.current().nextInt(ids.size());
        String id = ids.get(index);
        return providerRegistry.get(id);
    }
}
