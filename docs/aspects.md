# Aspects

Aspects are used by [systems](systems.md) to tell the framework what entities are processed by this
system. They basically say that entity should have components of given type to be processed.

To create an aspect you'll need to have defined some components. For now let's define some simple
component classes:

```scala
case class Position(var x: Float, var y: Float) extends Component
case class RenderInfo(var image: Image) extends Component
```

Now you can define aspects.

### Any

```scala
Aspect.any
```

Matches all entities.

### None

```scala
Aspect.none
```

Matches no entities.
### For all...

```scala
Aspect.forAll(classOf[Position], classOf[RenderInfo])
```

This will create aspect that matches all entities that have both `Position` and `RenderInfo`
components. Entity will be matched also if it has more components than that.


Alternatively you can write:

```scala
Aspect.forAll[Position, RenderInfo]
```

This is of course more appealing as it's cleaner than the previous method. There is (as always) a
small 'but'. You can use this method to create aspects for maximum of five component types. If you
need to create aspect with more than that - you have to use first notation.

### For one of...

```scala
Aspect.forOneOf(classOf[Position], classOf[RenderInfo])
Aspect.forOneOf[Position, RenderInfo]
```

Creates aspect that will match all entities that have at least one component listed here.


### Only for...

```scala
Aspect.onlyFor(classOf[Position], classOf[RenderInfo])
Aspect.onlyFor[Position, RenderInfo]
```

Creates aspect that will match entities that have **only** required components. This means that to
be matched entity should have only two components - `Position` and `RenderInfo`. If some entity have
also any other component, it won't be matched.
