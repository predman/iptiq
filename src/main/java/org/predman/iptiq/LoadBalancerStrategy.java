package org.predman.iptiq;

/**
 * Strategy that will return the next available provider based on a particular algorithm
 */
public interface LoadBalancerStrategy {
    
    Provider nextProvider();
}
