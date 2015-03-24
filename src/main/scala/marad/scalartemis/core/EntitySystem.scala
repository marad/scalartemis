package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, MutableBag}

import scala.reflect.ClassTag

abstract class EntitySystem(aspect: Aspect) {
  private val _entities = new MutableBag[Entity]

  def entities: Bag[Entity] = _entities

  def onRegister(entities: Bag[Entity]): Unit = entities.foreach(entityCreated)

  def entityCreated(entity: Entity): Unit =
    if (aspect ~ entity) {
      _entities.add(entity)
    }

  def entityDestroyed(entity: Entity): Unit =
    if (aspect ~ entity) {
      _entities.remove(entity)
    }

  def update(): Unit = process(_entities)

  def process(entities: Bag[Entity]): Unit

  // TODO: get component for entity
  def component[T:ClassTag](entity: Entity): Option[T] = ???
}
