package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, MutableBag}

abstract class EntitySystem(aspect: Aspect) {
  private val entities = new MutableBag[Entity]

  def onRegister(world: World): Unit = entities.foreach(entityCreated)

  def entityCreated(entity: Entity): Unit =
    if (aspect ~ entity) {
      entities.add(entity)
    }

  def entityDestroyed(entity: Entity): Unit =
    if (aspect ~ entity) {
      entities.remove(entity)
    }

  def update(): Unit = process(entities)

  def process(entities: Bag[Entity]): Unit
}

