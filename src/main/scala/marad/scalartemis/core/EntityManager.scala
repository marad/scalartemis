package marad.scalartemis.core

import utils.MutableBag

class EntityManager(val world: World) {
  private val activeEntities = new MutableBag[Entity]()
  private val removedAndAvailable = new MutableBag[Entity]()
  private var nextAvailableId = 0
  private var count = 0
  private var uniqueEntityId = 0l
  private var totalCreated = 0l
  private var totalRemoved = 0l

  private var componentsByType: MutableBag[MutableBag[Component]] = new MutableBag[MutableBag[Component]](64)
  private var entityComponents: MutableBag[Component] = new MutableBag[Component]()

  def create(): Entity = {
    val entity:Entity = removedAndAvailable.removeLast() match {
      case Some(e) =>
        e.reset()
        e
      case None =>
        nextAvailableId += 1
        new Entity(world, nextAvailableId)
    }

    uniqueEntityId += 1
    entity.uniqueId = uniqueEntityId
    activeEntities(entity.id) = entity
    count += 1
    totalCreated += 1
    entity
  }

  def remove(entity: Entity): Unit = {
    activeEntities(entity.id) = null.asInstanceOf[Entity]
    entity.typeBits = 0
    refresh(entity)

  }

  def removeComponentsOfEntity(entity: Entity): Unit = {

  }

  def refresh(entity: Entity): Unit = {

  }
}
