package marad.scalartemis

import marad.scalartemis.entity.Entity

abstract class SequentialProcessingSystem(aspect: Aspect) extends EntitySystem(aspect) {
  override def process(delta: Float): Unit = entities.foreach(process(_, delta))

  def process(entity: Entity, delta: Float): Unit
}
