package marad.scalartemis.core

import marad.scalartemis.core.utils.{Bag, MutableBag}

import scala.reflect.ClassTag

abstract class EntitySystem(aspect: Aspect) {
  private var _world: World = null

  def world = _world

  def onRegister(world: World): Unit = _world = world

  def update(): Unit = process(world.entities.filter(e => aspect ~ e))

  def process(entities: Iterable[Entity]): Unit

  // TODO: get component for entity
  def component[T:ClassTag](entity: Entity): Option[T] = ???
}
