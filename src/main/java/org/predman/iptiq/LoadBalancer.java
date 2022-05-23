package org.predman.iptiq;


public class LoadBalancer {
    
    private final ProviderRegistry providerRegistry;
    private final LoadBalancerStrategy strategy;
    
    public LoadBalancer() {
        providerRegistry = new ProviderRegistry();
        
        // NOTE: instructions did not specify how/where to set the strategy
        //        strategy = new RandomStrategy(providerRegistry);
        strategy = new RoundRobinStrategy(providerRegistry);
        
        // start the heartbeat monitor
        new HeartBeatMonitor(providerRegistry).start();
    }
    
    public void register(Provider provider) {
        providerRegistry.register(provider);
    }
    
    public String get() {
        return strategy.nextProvider().get();
    }
}
