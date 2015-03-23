package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, MutableBag}

abstract class EntitySystem {
  private val entities = new MutableBag[Entity]

  def onRegister(world: World): Unit = _
  def entityCreated(entity: Entity): Unit = {
    // TODO: add filtering by Aspect
    entities.add(entity)
  }

  def entityDestroyed(entity: Entity): Unit = entities.remove(entity)

  def update(): Unit = process(entities)
  def process(entities: Bag[Entity]): Unit
}
