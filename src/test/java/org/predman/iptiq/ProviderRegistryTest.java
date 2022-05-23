package org.predman.iptiq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ProviderRegistryTest {
    private ProviderRegistry providerRegistry;
    
    @BeforeEach
    public void beforeEach() {
        providerRegistry = new ProviderRegistry();
    }
    
    @DisplayName("Provider is required, if null throw an exception")
    @Test
    public void register_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> providerRegistry.register(null));
    }
    
    @DisplayName("Can register up to MAX providers")
    @Test
    public void register() {
        
        for (int i = 0; i < 10; i++) {
            providerRegistry.register(new Provider());
        }
    }
    
    @DisplayName("If registration would exceed the maximum number of providers, then throw an exception")
    @Test
    public void register_maximum_exceeded() {
        
        // register up to the max
        for (int i = 0; i < 10; i++) {
            providerRegistry.register(new Provider());
        }
        // register an additional to go over the max
        Assertions.assertThrows(ProviderMaxRegistrationException.class, () -> providerRegistry.register(new Provider()));
    }
    
    @DisplayName("If a provider is registered more than once, it should be equivalent to a single registration")
    @Test
    public void register_same_provider_more_than_once() {
        
        // NOTE: this is a bit hacky given the 'void' return type of 'register'...
        // if switching over to use java.util.Map semantics one could have more insight into whether the provider had previously been registered
        final Provider provider = new Provider();
        
        // register up to the max + 1
        for (int i = 0; i < (10 + 1); i++) {
            providerRegistry.register(provider);
        }
    }
    
}
