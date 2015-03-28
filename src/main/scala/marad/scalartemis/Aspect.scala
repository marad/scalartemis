package marad.scalartemis

import marad.scalartemis.entity.ComponentTypeManager
import ComponentTypeManager.ComponentClass

import scala.collection.BitSet

trait Aspect {
  def ~(types: BitSet): Boolean
}

class AspectAll(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(typeSet: BitSet): Boolean = (set & typeSet) == set
}

class AspectOneOf(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(types: BitSet): Boolean = (set & types).nonEmpty
}

class AspectOnly(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(types: BitSet): Boolean = (set ^ types).isEmpty
}

object AspectAny extends Aspect {
  override def ~(types: BitSet): Boolean = true
}

object AspectNone extends Aspect {
  override def ~(types: BitSet): Boolean = false
}

object Aspect {
  def forAll(componentClasses: ComponentClass*): Aspect =
    new AspectAll(componentClasses.map(ComponentTypeManager.getTypeFor):_*)

  def forOneOf(componentClasses: ComponentClass*): Aspect =
    new AspectOneOf(componentClasses.map(ComponentTypeManager.getTypeFor):_*)

  def onlyFor(componentClasses: ComponentClass*): Aspect =
    new AspectOnly(componentClasses.map(ComponentTypeManager.getTypeFor):_*)

  def any = AspectAny
  def none = AspectNone
}