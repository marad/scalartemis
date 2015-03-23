# scalartemis

I firstly wanted to just rewrite the Artemis framework to Scala, but I discovered that the architecture of 
the Artemis framework doesn't meet my expectations. So Artemis gave me the overall view what should it 
look like. Almost everything else is entirely new.


## Bags
I've took the liberty to use this concept of `Bag` and `ImmutableBag` of items from Artemis - this is actually 
the only piece of code that has been just ported (with some improvements) from original Artemis framework. 
The only difference is that by I changed their names to `MutableBag` and `Bag` respectively.

## Development
This project is now under development and it's not ready for use (nothing works, yet! :))
