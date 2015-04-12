# World

`World` class is the beginning of everything. It holds entity systems, entities, and their
components. To create new world you can simply create an instance of it:

```scala
val world = new World
```

## World API

* **update(delta: Float): Unit**

    > Invokes processing the systems.

### Entity Management

* **createEntity(components: Component*): Entity**

    > Creates an entity with given components.

* **destroyEntity(entity: Entity): Unit**

    > Destroys given entity. After destruction this entity identifier may be assigned to another
    entity during creation.

### Component Management
* **addComponent(entity: Entity, component: Component): Unit**

    > Adds a component to entity.

* **removeComponent(entity: Entity, component: ComponentType): Unit**

    > Removes a component of given type from entity.

* **getComponent(entity: Entity, component: ComponentType): Option[Component]**

    > Returns a component of given type if the entity has one.

### Entity Sets
* **getEntitySet(aspect: Aspect): EntitySet**

    > Returns EntitySet that has all the entities that have certain aspect (set of components). This
    set is automatically updated. When new entity with required components is created - it's added
    to this set. When some entity is removed (or it's component is removed and it no longer have
    required aspect) it's also removed from the set.

### Systems

* **systems: Bag[EntitySystem]**

    > Returns immutable bag of entity systems registered for that world.

* **registerSystem(entitySystem: EntitySystem): Unit**

    > Registers new entity system in the world.
