package marad.scalartemis.core.utils

import scala.reflect.ClassTag

trait Bag[T] extends Iterable[T] {
  def capacity: Int
  def apply(index: Int): Option[T]
  def get(index: Int): Option[T]
  def contains(o: T): Boolean
}

object Bag {
  def apply[T:ClassTag](args: T*): Bag[T] = MutableBag(args:_*)
}
