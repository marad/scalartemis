package marad.scalartemis

import marad.scalartemis.entity.Entity

import scala.reflect.{ClassTag, classTag}

trait ComponentMapping {
  def world: World

  def component[A <: Component : ClassTag](entity: Entity): Option[A] =
    world.getComponent(entity,
      ComponentTypeManager.getTypeFor(
        classTag[A].runtimeClass.asSubclass(classOf[Component])
      )
    ).asInstanceOf[Option[A]]
}
