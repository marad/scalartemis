package marad.scalartemis

import marad.scalartemis.core.utils.{Bag, EntitySet}

/**
 * Processes entities
 * @param aspect Determines which entities are processed by the system
 */
abstract class EntitySystem(val aspect: Aspect) {
  private var entitySet: EntitySet = null

  /**
   * The only way for inheriting class to get the entities processed
   * by this entity system.
   * @return Entities with required aspect
   */
  def entities: Bag[Entity] = entitySet.entities

  /**
   * Invoked when the system is registered to the world
   * @param entitySet Automatically updating set of entities with required components
   */
  def onRegister(entitySet: EntitySet) = this.entitySet = entitySet

  /**
   * Invoked every world update
   * @param delta World time delta value
   */
  def process(delta: Float): Unit
}
