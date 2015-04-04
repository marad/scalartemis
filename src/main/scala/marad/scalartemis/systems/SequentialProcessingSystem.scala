package marad.scalartemis.systems

import marad.scalartemis.{Aspect, Entity, EntitySystem}

/**
 * Processes entities one by one
 * @param aspect Aspect for deciding which components are required for entity
 *               to be processed by the system
 */
abstract class SequentialProcessingSystem(aspect: Aspect) extends EntitySystem(aspect) {
  override def process(delta: Float): Unit = entities.foreach(process(_, delta))

  def process(entity: Entity, delta: Float): Unit
}
