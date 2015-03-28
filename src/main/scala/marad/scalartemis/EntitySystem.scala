package marad.scalartemis

import marad.scalartemis.entity.Entity

abstract class EntitySystem(val aspect: Aspect) {
  def process(entity: Entity, delta: Float): Unit
}
