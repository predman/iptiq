package org.predman.iptiq;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ProviderRegistry {
    
    private static final int MAX_PROVIDERS = 10;
    private final Map<String, Provider> providers;
    
    public ProviderRegistry() {
        providers = new LinkedHashMap<>();
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
    
    public List<String> getIds() {
        return new ArrayList<>(providers.keySet());
    }
    
    public Provider get(String id) {
        return providers.get(id);
    }
}
