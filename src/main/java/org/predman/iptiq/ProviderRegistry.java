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
    
    public synchronized void register(Provider provider) {
        
        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        
        if (providers.size() >= MAX_PROVIDERS) {
            throw new ProviderMaxRegistrationException();
        }
        
        providers.put(provider.get(), new ProviderHolder(provider));
    }
    
    public synchronized List<String> getIds() {
        return providers.keySet()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }
    
    public synchronized List<String> getActiveIds() {
        return providers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().enabled())
                .map(entry -> entry.getKey())
                .collect(Collectors.toUnmodifiableList());
    }
    
    public synchronized void markUp(String id) {
        ProviderHolder holder = providers.get(id);
        if (holder != null) {
            holder.markUp();
        }
    }
    
    public synchronized void markDown(String id) {
        ProviderHolder holder = providers.get(id);
        if (holder != null) {
            holder.markDown();
        }
    }
    
    public synchronized Provider get(String id) {
        ProviderHolder holder = providers.get(id);
        if (holder == null) {
            return null;
        }
        return holder.getProvider();
    }
    
    // package-private for testability
    static class ProviderHolder {
        
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
            // small optimization to avoid wrap around after Integer.MAX_VALUE checks
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
