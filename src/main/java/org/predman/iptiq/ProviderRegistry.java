package org.predman.iptiq;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProviderRegistry {
    
    private static final int MAX_PROVIDERS = 10;
    // registry (database) of all registered providers
    private final Map<String, ProviderHolder> providers;
    
    public ProviderRegistry() {
        // choose 'linked' to maintain ordering of keys
        providers = new LinkedHashMap<>();
    }
    
    public void register(Provider provider) {
        
        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        
        if (providers.size() >= MAX_PROVIDERS) {
            throw new ProviderMaxRegistrationException();
        }
        
        providers.put(provider.get(), new ProviderHolder(provider));
    }
    
    public List<String> getIds() {
        return providers.keySet()
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<String> getActiveIds() {
        return providers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().enabled())
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }
    
    public void markUp(String id) {
        // FIXME: NPE
        providers.get(id).markUp();
    }
    
    public void markDown(String id) {
        // FIXME: NPE
        providers.get(id).markDown();
    }
    
    public Provider get(String id) {
        ProviderHolder holder = providers.get(id);
        if (holder == null) {
            return null;
        }
        return holder.getProvider();
    }
    
    public static class ProviderHolder {
        
        private static final int MIN_UP_COUNT = 2;
        private Provider provider;
        private int upCount;
        
        public ProviderHolder(Provider provider) {
            this.provider = provider;
            upCount = MIN_UP_COUNT;
        }
        
        public Provider getProvider() {
            return provider;
        }
        
        public void markUp() {
            if (upCount < MIN_UP_COUNT) {
                upCount++;
            }
        }
        
        public void markDown() {
            upCount = 0;
        }
        
        public boolean enabled() {
            return upCount >= MIN_UP_COUNT;
        }
    }
}
