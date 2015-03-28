package marad.scalartemis.core

import marad.scalartemis.core.ComponentTypeManager.ComponentClass

import scala.collection.BitSet

abstract class Aspect {
  def ~(entity: Entity): Boolean = this.~(entity.componentTypes)
  def ~(types: BitSet): Boolean
}

class AspectAll(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(types: BitSet): Boolean = (set & types) == set
}

class AspectOneOf(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(types: BitSet): Boolean = (set & types).nonEmpty
}

class AspectOnly(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(types: BitSet): Boolean = (set ^ types).isEmpty
}

object Aspect {
  def forAll(componentClasses: ComponentClass*): Aspect =
    new AspectAll(componentClasses.map(ComponentTypeManager.getTypeFor):_*)

  def forOneOf(componentClasses: ComponentClass*): Aspect =
    new AspectOneOf(componentClasses.map(ComponentTypeManager.getTypeFor):_*)

  def onlyFor(componentClasses: ComponentClass*): Aspect =
    new AspectOnly(componentClasses.map(ComponentTypeManager.getTypeFor):_*)
}