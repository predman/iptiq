package org.predman.iptiq;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class HeartBeatMonitor {
    
    private static final int HEARTBEAT_CHECK_INTERVAL = 5;
    private final HeartBeatChecker checker;
    
    public HeartBeatMonitor(ProviderRegistry providerRegistry) {
        checker = new HeartBeatChecker(providerRegistry);
    }
    
    public void start() {
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                checker.checkAll();
            }
        }, 0, HEARTBEAT_CHECK_INTERVAL, TimeUnit.SECONDS);
    }
}
