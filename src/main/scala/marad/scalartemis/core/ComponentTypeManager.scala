package marad.scalartemis.core

import scala.collection.mutable

object ComponentTypeManager {
  type ComponentClass = Class[_ <: Component]
  val componentTypes = mutable.Map[ComponentClass, ComponentType]()

  def getTypeFor(c : ComponentClass): ComponentType = componentTypes.get(c) match {
    case Some(t) => t
    case None =>
      val componentType = new ComponentType()
      componentTypes += c -> componentType
      componentType
  }

  def getBit(c: ComponentClass): Long = getTypeFor(c).bit
  def getId(c: ComponentClass): Int = getTypeFor(c).id
}
