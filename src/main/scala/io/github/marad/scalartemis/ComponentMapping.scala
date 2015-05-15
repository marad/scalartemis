package io.github.marad.scalartemis

import io.github.marad.scalartemis.core.ComponentTypeManager

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
