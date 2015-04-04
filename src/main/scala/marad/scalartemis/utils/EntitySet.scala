package marad.scalartemis.utils

import marad.scalartemis.entity.Entity

class EntitySet {
  private val _entities = new MutableBag[Entity]

  def entities: Bag[Entity] = _entities
  def addEntity(entity: Entity) = _entities.add(entity)
  def removeEntity(entity: Entity) = _entities.remove(entity)
}
