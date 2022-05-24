package org.predman.iptiq;

import java.util.List;

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
    
    @Test
    public void get_bad_id() {
        Assertions.assertNull(providerRegistry.get("bad-id"), "Invalid provider id should return null");
    }
    
    @Test
    public void get_good_id() {
        Provider provider = new Provider();
        providerRegistry.register(provider);
        Assertions.assertEquals(provider, providerRegistry.get(provider.get()), "Valid provider id should return that provider");
    }
    
    @DisplayName("Ensure id list is complete and maintains order registered")
    @Test
    public void get_ids() {
        Provider[] providers = new Provider[] {
                new Provider(),
                new Provider(),
                new Provider(),
                new Provider()
        };
        // register a few providers
        for (int i = 0; i < providers.length; i++) {
            providerRegistry.register(providers[i]);
        }
        List<String> ids = providerRegistry.getIds();
        Assertions.assertEquals(providers.length, ids.size());
        for (int i = 0; i < providers.length; i++) {
            Assertions.assertEquals(providers[i].get(), ids.get(i));
        }
    }
    
    @DisplayName("Ensure behavior when no providers are registered")
    @Test
    public void get_ids_zero() {
        Assertions.assertEquals(0, providerRegistry.getIds().size());
    }
    
    @DisplayName("Ensure enabled/active id list is complete and maintains order registered")
    @Test
    public void get_active_ids() {
        Provider[] providers = new Provider[] {
                new Provider(),
                new Provider(),
                new Provider(),
                new Provider()
        };
        // register a few providers
        for (int i = 0; i < providers.length; i++) {
            providerRegistry.register(providers[i]);
        }
        // mark a provider as down
        providerRegistry.markDown(providers[1].get());
        
        List<String> ids = providerRegistry.getActiveIds();
        Assertions.assertEquals(providers.length - 1, ids.size());
    }
    
    @DisplayName("Ensure behavior when no providers are registered")
    @Test
    public void get_active_ids_zero() {
        Assertions.assertEquals(0, providerRegistry.getActiveIds().size());
    }
    
    @Test
    public void holder_enabled_by_default() {
        
        ProviderRegistry.ProviderHolder holder = new ProviderRegistry.ProviderHolder(new Provider());
        Assertions.assertTrue(holder.enabled());
    }
    
    @Test
    public void holder_disabled_when_marked_as_down() {
        
        ProviderRegistry.ProviderHolder holder = new ProviderRegistry.ProviderHolder(new Provider());
        holder.markDown();
        Assertions.assertFalse(holder.enabled());
    }
    
    @DisplayName("If a node has been previously excluded from the balancing it should be re-included if it has successfully been 'heartbeat checked' for 2 consecutive times")
    @Test
    public void holder_enabled_after_two_successful_up_checks() {
        
        ProviderRegistry.ProviderHolder holder = new ProviderRegistry.ProviderHolder(new Provider());
        // holder marked down, is down
        holder.markDown();
        Assertions.assertFalse(holder.enabled());
        // holder marked up, is still down
        holder.markUp();
        Assertions.assertFalse(holder.enabled());
        // holder marked up a 2nd time, is now up/enabled
        holder.markUp();
        Assertions.assertTrue(holder.enabled());
    }
}
