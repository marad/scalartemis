package marad.scalartemis.core.utils

import marad.scalartemis.Entity

class EntitySet {
  private val _entities = new MutableBag[Entity]

  def entities: Bag[Entity] = _entities
  def addEntity(entity: Entity) = _entities.add(entity)
  def removeEntity(entity: Entity) = _entities.remove(entity)
}
