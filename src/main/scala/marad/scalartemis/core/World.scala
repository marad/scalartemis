package marad.scalartemis.core

import marad.scalartemis.core.utils.{IdGenerator, MutableBag}

class World {
  val entityIdGenerator = new IdGenerator
  val entities = new MutableBag[Entity]()
  val systems = new MutableBag[EntitySystem]()

  def initialize() = ???
  def update(delta: Float) = systems.foreach(_.update())
  // TODO: cannot register system after initialize was called
  // TODO OR: pass required entities to entity system
  def registerSystem(entitySystem: EntitySystem) = {
    systems.add(entitySystem)
    entitySystem.onRegister(this)
  }

  def componentAdded(entity: Entity, component: Component) = ???
  def componentRemoved(entity: Entity, component: Component) = ???

  def createEntity() = {
    val entity = new Entity(this, entityIdGenerator.nextId)
    entities.add(entity)
    systems.foreach(_.entityCreated(entity))
    entity
  }

  def destroyEntity(entity: Entity) = {
    entities.remove(entity)
    systems.foreach(_.entityDestroyed(entity))
  }

}

object World {
//  type ComponentAddCallback = (Entity, Component) => Unit
//  type ComponentRemoveCallback = (Entity, Component) => Unit
//  type EntityCreatedCallback = Entity => Unit
//  type EntityDestroyedCallback = Entity => Unit
}
