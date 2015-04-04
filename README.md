# scalartemis

I firstly wanted to just rewrite the Artemis framework to Scala, but I discovered that the architecture of 
the Artemis framework doesn't meet my expectations. So Artemis gave me the overall view what should it 
look like. Almost everything else is entirely new.

## Bags
I've took the liberty to use this concept of `Bag` and `ImmutableBag` of items from Artemis - this is actually 
the only piece of code that has been just ported (with some improvements) from original Artemis framework. 
The only difference is that by I changed their names to `MutableBag` and `Bag` respectively.

## Development
This project is under development but should mostly work already ;)

## Example 

```scala
case class Position(var x: Int, var y: Int) extends Component

class PositionUpdateSystem(val world: World)
      extends SequentialProcessingSystem(Aspect.forAll[Position])
      with ComponentMapping {
  override def process(entity: Entity, delta: Float): Unit = {
    component[Position](entity) match {
      case Some(position) =>
        position.x += 1
        position.y += 1
      case None =>
    }
  }
}

val world = new World
world.registerSystem(new PositionUpdateSystem(world))
world.createEntity(Position(1, 2))
world.update(1f)
```
