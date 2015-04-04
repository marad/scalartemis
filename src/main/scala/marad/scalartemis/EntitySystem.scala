package marad.scalartemis

import marad.scalartemis.entity.Entity
import marad.scalartemis.utils.EntitySet

abstract class EntitySystem(val aspect: Aspect) {
  private var entitySet: EntitySet = null

  def onRegister(entitySet: EntitySet) = this.entitySet = entitySet

  def process(entity: Entity, delta: Float): Unit
}
