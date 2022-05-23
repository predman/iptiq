package org.predman.iptiq;


public class LoadBalancer {
    
    private final ProviderRegistry providerRegistry;
    private final LoadBalancerStrategy strategy;
    
    public LoadBalancer() {
        providerRegistry = new ProviderRegistry();
        strategy = new RandomStrategy(providerRegistry);
    }
    
    public void register(Provider provider) {
        providerRegistry.register(provider);
    }
    
    public String get() {
        return strategy.nextProvider().get();
    }
}
