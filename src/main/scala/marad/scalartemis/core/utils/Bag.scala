package marad.scalartemis.core.utils

trait Bag[T] extends Iterable[T] {
  def capacity: Int
  def apply(index: Int): Option[T]
  def get(index: Int): Option[T]
  def contains(o: T): Boolean
}
