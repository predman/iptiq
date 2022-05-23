package org.predman.iptiq;

import java.util.HashMap;
import java.util.Map;


public class LoadBalancer {
    
    private static final int MAX_PROVIDERS = 10;
    private final Map<String, Provider> providers;
    
    public LoadBalancer() {
        providers = new HashMap<>();
    }
    
    public void register(Provider provider) {
        
        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        
        if (providers.size() >= MAX_PROVIDERS) {
            throw new ProviderMaxRegistrationException();
        }
        
        providers.put(provider.get(), provider);
    }
}
