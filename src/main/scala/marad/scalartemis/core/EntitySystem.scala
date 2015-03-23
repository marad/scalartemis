package marad.scalartemis.core

import marad.scalartemis.core.ComponentTypeManager.ComponentClass
import marad.scalartemis.core.utils.{Bag, MutableBag}

abstract class EntitySystem(componentClasses: ComponentClass*) {
  private val entities = new MutableBag[Entity]
  def onRegister(world: World): Unit = {}
  def entityCreated(entity: Entity): Unit = {
    // TODO: add filtering by Aspect
    entities.add(entity)
  }

  def entityDestroyed(entity: Entity): Unit = entities.remove(entity)

  def update(): Unit = process(entities)
  def process(entities: Bag[Entity]): Unit
}

