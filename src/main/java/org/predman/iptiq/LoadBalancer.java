package org.predman.iptiq;


import java.util.concurrent.atomic.AtomicInteger;


public class LoadBalancer {
    
    private static final int MAX_PARALLEL_REQUESTS_PER_PROVIDER = 5;
    
    private AtomicInteger requestCount;
    private final ProviderRegistry providerRegistry;
    private final LoadBalancerStrategy strategy;
    
    public LoadBalancer() {
        providerRegistry = new ProviderRegistry();
        
        // NOTE: instructions did not specify how/where to set the strategy
        //        strategy = new RandomStrategy(providerRegistry);
        strategy = new RoundRobinStrategy(providerRegistry);
        
        // initialize the request counter to zero (since no requests yet on startup)
        requestCount = new AtomicInteger(0);
        
        // start the heartbeat monitor
        new HeartBeatMonitor(providerRegistry).start();
    }
    
    public void register(Provider provider) {
        providerRegistry.register(provider);
    }
    
    public String get() {
        // NOTE: 'null' here represents something like an http 503 unavailable response
        String value = null;
        int activeProviders = providerRegistry.getActiveIds().size();
        /*
        Here we increment so that we 'acquire' the next available request, then check if over the max (rather than get + check then increment).
        We always decrement regardless of whether we were able to proceed with the request or not.
         */
        if (requestCount.incrementAndGet() <= activeProviders * MAX_PARALLEL_REQUESTS_PER_PROVIDER) {
            value = doGet();
        }
        requestCount.decrementAndGet();
        return value;
    }
    
    private String doGet() {
        Provider provider = strategy.nextProvider();
        if (provider == null) {
            return null;
        }
        return provider.get();
    }
}
