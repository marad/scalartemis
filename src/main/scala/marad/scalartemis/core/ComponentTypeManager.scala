package marad.scalartemis.core

import marad.scalartemis.core.utils.IdGenerator

import scala.collection.mutable

object ComponentTypeManager {
  private val idGenerator = new IdGenerator
  type ComponentClass = Class[_ <: Component]
  val componentTypes = mutable.Map[ComponentClass, ComponentType]()

  def getTypeFor(c : ComponentClass): ComponentType = componentTypes.get(c) match {
    case Some(t) => t
    case None =>
      val componentType = new ComponentType(idGenerator.nextId)
      componentTypes += c -> componentType
      componentType
  }

  def getBit(c: ComponentClass): Long = getTypeFor(c).bit
  def getId(c: ComponentClass): Int = getTypeFor(c).id
}
