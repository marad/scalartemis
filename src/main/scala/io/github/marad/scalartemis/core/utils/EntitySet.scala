package io.github.marad.scalartemis.core.utils

import io.github.marad.scalartemis.Entity

import scala.collection.mutable

class EntitySet {
  private val _entityIds = new mutable.BitSet
  private val _entities = new MutableBag[Entity]

  def entities: Bag[Entity] = _entities

  def contains(entity: Entity) = _entityIds.contains(entity.id)

  def add(entity: Entity) =
    if (!contains(entity)) {
      _entityIds += entity.id
      _entities.add(entity)
    }

  def removeIfContained(entity: Entity) =
    if (contains(entity)) {
      _entities.remove(entity)
      _entityIds -= entity.id
    }
}
