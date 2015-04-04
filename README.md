# scalartemis

I firstly wanted to just rewrite the Artemis framework to Scala, but I discovered that the architecture of 
the Artemis framework doesn't meet my expectations. So Artemis gave me the overall view what should it 
look like. Almost everything else is entirely new.

## Development
This project is under development but should mostly work already ;)

## Usage
For now I don't offer any binary files to be used. If you want to use the library you should clone the repository and run task:
>      sbt package

This will generate JAR file in `target/scala-2.11/` folder.

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

In the example above there is one component `Position` which holds the coordinates. There is also `PositionUpdateSystem` that contains the logic - what should happen each world update to every entity with `Position` component. Here we declare our system as subclass of `SequentialProcessingSystem` which means that all entities are processed one by one.

Also please note that `SequentialProcessingSystem` has constructor argument of type `Aspect`. This argument is then used to determine which entities are handled by the system - it will receive only entities matching requirements (containing required components).

We can also see that the `ComponentMapping` trait is used. This allows us to call method `component[T <: Component](entity: Entity)` which returns `Option[T]`. This method is used to fetch component of given type for given entity.

Below the `PositionUpdateSystem` class declaration we create the `World` object and register our system in it. Then we create one entity with one component `Position(1, 2)`. In the end there is one call `world.update(1f)` which asks the world to process all systems.
