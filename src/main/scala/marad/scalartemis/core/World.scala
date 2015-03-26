package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, IdGenerator, MutableBag}

class World {
  private val _entityIdGenerator = new IdGenerator
  private val _systems = new MutableBag[EntitySystem]()
  private val entityManager = new EntityManager(this)

  def entities: Bag[Entity] = entityManager.entities

  def update(delta: Float) = _systems.foreach(_.update())

  def registerSystem(entitySystem: EntitySystem) = {
    _systems.add(entitySystem)
    entitySystem.onRegister(this)
  }

  def componentAdded(entity: Entity, component: Component): Unit = {}

  def componentRemoved(entity: Entity, component: Component): Unit = {}

  def createEntity(components: Component*) = {
    val entity = entityManager.acquireEntity()
    components.foreach(entity.addComponent)
    entity
  }

  def destroyEntity(entity: Entity) = {
    entityManager.releaseEntity(entity)
  }
}
