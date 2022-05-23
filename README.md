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

Develop an algorithm that, when invoking multiple times the Load Balancer on its get() method, should cause the random invocation of the get() method of any registered provider instance.

