# iptiq coding exercise : Load Balancer

## Step 1

Generate a Provider that, once invoked on his get() method, retrieve an unique identifier (string) of the provider instance

## Step 2

Register a list of provider instances to the Load Balancer - the maximum number of providers accepted from the load balancer is 10

- [ ] Question: what is the return value of the 'register' method?
- [ ] Question: what is the behavior when a provider is registered a 2nd time?
- [ ] Question: what is the behavior when the 11th register attempt is made? (return 'false' / throw exception / ignore)

NOTES:

- can emulate java.util.Map semantics (i.e. return the value (provider) from the register method)
- for this step, assumes single-threaded access to register (i.e. not thread safe)

## Step 3

Develop an algorithm that, when invoking multiple times the Load Balancer on its get() method, should cause the random invocation of the get() method of any registered provider
instance.

## Step 4

Develop an algorithm that, when invoking multiple times the Load Balancer on its get() method, should cause the round-robin (sequential) invocation of the get() method of the
registered providers.

## Step 5

Develop the possibility to exclude / include a specific provider into the balancer

## Step 6

The load balancer should invoke every X seconds each of its registered providers on a special method called check() to discover if they are alive – if not, it should exclude the
provider node from load balancing.

## Step 7

If a node has been previously excluded from the balancing it should be re-included if it has successfully been “heartbeat checked” for 2 consecutive times

## Step 8

Assuming that each provider can handle a maximum number of Y parallel requests, the Balancer should not accept any further request when it has (Y*aliveproviders) incoming requests
running simultaneously
