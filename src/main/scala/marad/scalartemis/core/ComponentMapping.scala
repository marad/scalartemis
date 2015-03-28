package marad.scalartemis.core

import marad.scalartemis.core.entity.{ComponentTypeManager, Entity}

import scala.reflect.{ClassTag, classTag}

trait ComponentMapping {
  val world: World

  def component[A <: Component : ClassTag](entity: Entity): Option[A] =
    world.getComponent(entity,
      ComponentTypeManager.getTypeFor(
        classTag[A].runtimeClass.asSubclass(classOf[Component])
      )
    ).asInstanceOf[Option[A]]
}
