package marad.scalartemis.core

import marad.scalartemis.Entity
import marad.scalartemis.core.utils.{Bag, DynamicArray, IdGenerator, MutableBag}

import scala.collection.mutable

/**
 * This class manages the entities. It reuses free entity IDs.
 * Also allows to get Bag of entities or quickly find
 * entities by id.
 */
class EntityManager {
  private val entityIdGenerator = new IdGenerator
  private val _entities = new MutableBag[Entity]
  private val entitiesById = new DynamicArray[Entity]
  private val freeIds = new mutable.ArrayStack[Int]

  def entityCount = _entities.size

  def entities: Bag[Entity] = _entities

  def get(entityId: Int) = entitiesById(entityId)

  def acquireEntity(): Entity = {
    val id = if (freeIds.size > 0) freeIds.pop() else entityIdGenerator.nextId
    val entity = new Entity(id)
    _entities.add(entity)
    entitiesById(entity.id) = entity
    entity
  }

  def releaseEntity(id: Int): Unit = {
    _entities.remove(entitiesById(id))
    entitiesById.remove(id)
    freeIds += id
  }
}