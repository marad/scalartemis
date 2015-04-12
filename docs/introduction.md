# Scalartemis Overview
Scalartemis is entity component system which means that the entities are represented by set of components that holds only data. Components should not contain any logic that is not related directly to the data they hold. The right place to put any logic are the entity systems, which are used to process entities that have the right set of components.

It's designed so that user can query for entities and components extremely fast. All collections used in the library use `Array`s underneath to be able to quickly access any required data. Of course there is a price for this. In some cases you may notice increased use of memory, and you cannot assume that entities are processed in the same order they were created.

## The Name
Name Scalartemis is just a combination of two words. First is _Scala_ which, as you probably already know, is the language that it's written in. Second is _Artemis_. Artemis is a project created in Java language which is also a component entity system.

In fact at the beginning all I wanted was to rewrite Artemis in Scala. During the process of rewriting I noticed more and more things that could be improved. After trying to improve almost everything I decided that the whole thing should be rewritten from scratch. This is how Scalartemis was born!

What is different? Well - I try to keep classes and traits maintainable by reducing their size to minimum. `World` class is quite problematic. This is main class that has a lot of functionality. Thanks to Scala's traits I was able to split it up into smaller modules that are easier to read and understand. Also Scalartemis is covered with unit tests which guarantees that updates and changes doesn't break anything.

I also introduced some new data structures `DynamicArray` or `EntitySet`. Feel free to look the code up to see their purpose (don't forget to read their tests).

In the end Scalartemis is similar to the Artemis library because it uses the same concept and I kept some class names (like `Aspect`) but deep inside it works very differently.

## Key concepts
Key concepts that Scalartemis is build on are listed here: <http://entity-systems.wikidot.com/fast-entity-component-system>.
