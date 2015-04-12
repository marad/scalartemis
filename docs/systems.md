# Systems

## Purpose of Systems

While [components](components.md) holds data for entities, systems do the work. Entity systems use
[Aspects](aspects.md) to define the entities they want to know about. When the system is registered
it's given an `EntitySet` object which always contains all entities matching required aspect. This
set is automatically updated as the entities are created and destroyed.

## Available Abstract Systems

By default Scalartemis has few entity systems that you can extend to get the behavior you need.

### EntitySystem

This is the most basic entity system. When extending it you'll have to overwrite the method:

```scala
def process(delta: Float): Unit
```

To get all entities that the system processes you need to invoke the `entities` property. It returns
immutable `Bag` of entities.


### SequentialProcessingSystem

This system processes your entities, well... sequentially. As it's abstract class extending it
requires you to override the method:

```scala
def process(entity: Entity, delta: Float): Unit
```

On world update this method will be called for every entity that matches the aspect defined for this
entity system.

### VoidEntitySystem

This system has `Aspect.none`. This means that it's not going to process any entities. What's the
use for this? If you need to do some work every time the world updates - this is the system you
should extend.

## Component Mapping

There is also a `ComponentMapping` helper trait. This trait requires your class to have the `World`
object as a field/property. It gives you one convenient method to get the component for given
entity:

```scala
class SomeSystem(val world: World)
extends SequentialProcessingSystem(Aspect.all)
with ComponentMapping {
  override def process(entity: Entity, delta: Float) = {
    component[Position](entity) match {
      case Some(position) => // ...
      case None => // ...
    }
  }
}
```
