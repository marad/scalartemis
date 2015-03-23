package marad.scalartemis.core

import scala.collection.BitSet

//class Aspect private (val value: Long) extends AnyVal {
//  def ~(componentType: ComponentType): Boolean =
//    ((value & componentType.bit) >> componentType.id) == 1
//}


abstract class Aspect {
  def ~(entity: Entity): Boolean
}

class AspectAll(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(entity: Entity): Boolean = ???
}

class AspectOneOf(types: ComponentType*) extends Aspect {
  val set = BitSet(types.map(_.id):_*)
  override def ~(entity: Entity): Boolean = ???
}

object Aspect {
//  def apply(value: Long): Aspect = new Aspect(value)
//  def apply(componentType: ComponentType): Aspect = new Aspect(componentType.bit)
//  def apply(types: ComponentType*): Aspect =
//    new Aspect(types.foldLeft(0l) { (acc, el) => acc | el.bit })
//  def forAll()
}