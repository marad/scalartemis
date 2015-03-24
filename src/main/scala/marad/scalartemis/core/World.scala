package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, IdGenerator, MutableBag}

class World {
  private val _entityIdGenerator = new IdGenerator
  private val _entities = new MutableBag[Entity]()
  private val _systems = new MutableBag[EntitySystem]()

  def entities: Bag[Entity] = _entities

  def initialize() = ???
  def update(delta: Float) = _systems.foreach(_.update())
  // TODO: cannot register system after initialize was called
  // TODO OR: pass required _entities to entity system
  def registerSystem(entitySystem: EntitySystem) = {
    _systems.add(entitySystem)
    entitySystem.onRegister(this)
  }

  def componentAdded(entity: Entity, component: Component) = ???
  def componentRemoved(entity: Entity, component: Component) = ???

  def createEntity(components: Component*) = {
    val entity = new Entity(this, _entityIdGenerator.nextId)
    components.foreach(entity.addComponent)
    _entities.add(entity)
    _systems.foreach(_.entityCreated(entity))
    entity
  }

  def destroyEntity(entity: Entity) = {
    _entities.remove(entity)
    _systems.foreach(_.entityDestroyed(entity))
  }


}

object World {
//  type ComponentAddCallback = (Entity, Component) => Unit
//  type ComponentRemoveCallback = (Entity, Component) => Unit
//  type EntityCreatedCallback = Entity => Unit
//  type EntityDestroyedCallback = Entity => Unit
}
