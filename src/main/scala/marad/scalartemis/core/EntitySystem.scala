package marad.scalartemis.core

import marad.scalartemis.core.entity.Entity

abstract class EntitySystem(val aspect: Aspect) {
  def process(entity: Entity, delta: Float): Unit
}
