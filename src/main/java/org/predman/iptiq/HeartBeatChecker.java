package org.predman.iptiq;

public class HeartBeatChecker {
    
    private final ProviderRegistry providerRegistry;
    
    public HeartBeatChecker(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }
    
    public void checkAll() {
        providerRegistry.getIds()
                .stream()
                .forEach(id -> {
                    Checkable provider = providerRegistry.get(id);
                    if (provider.check()) {
                        providerRegistry.markUp(id);
                    } else {
                        providerRegistry.markDown(id);
                    }
                });
    }
}
