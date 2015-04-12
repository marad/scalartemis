# Components

Components are used as parts of entities. They hold data for the entities. They shouldn't contain
any logic - only data structures.

If you need to have some additional information about the entity you'll need to create a component
for it. Let us imagine that you want to draw your entity on screen. For most games you probably
going to display something on the screen. You could create a component that holds all data related
to given entity - image (for 2D) or mesh (for 3D).

Notice that I didn't suggest putting position information there. This is because not all
positionable entities need to be also rendered. Let's take sound effect for example. Sound effect
can have position (sound source in the world) but it does not necessarily need to have image or
mesh.

## Creating Component

To create your own component you only need to extend the `Component` class:

```scala
case class Position(var x: Float, var y: Float) extends Component
```

Then you can simply create entity with this component:

```scala
world.createEntity(Position(10, 20))
```

## Component Type

Every component has it's own type. This is basically a number which identifies given component
class. This number is then used as index when storing components for given entity and to quickly
check if given entity has required components attached (has required [Aspect](aspects.md)). You
should not need to use component type directly in your games.

## Restrictions

Any entity can have only one component of each type. If you, for whatever reason, want to add
multiple components of the same type to one entity you can just create a component that aggregates
those components.
