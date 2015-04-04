package marad.scalartemis

import marad.scalartemis.core.utils.{Bag, EntitySet}

abstract class EntitySystem(val aspect: Aspect) {
  private var entitySet: EntitySet = null

  def entities: Bag[Entity] = entitySet.entities

  def onRegister(entitySet: EntitySet) = this.entitySet = entitySet

  def process(delta: Float): Unit
}
