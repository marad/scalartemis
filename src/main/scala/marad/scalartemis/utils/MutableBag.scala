package marad.scalartemis.utils

import scala.reflect.ClassTag

class MutableBag[T:ClassTag](_capacity: Int = 16) extends Bag[T] {
  protected var _data: Array[T] = Array.ofDim[T](_capacity)
  protected var _size: Int = 0

  override def size = _size
  override def isEmpty = _size == 0
  def capacity = _data.length

  def add(element: T): Unit = {
    if (_size == capacity) {
      grow()
    }
    _data(_size) = element
    _size += 1
  }

  def addAll(elements: Seq[T]): Unit = elements.foreach(add)
  def update(index: Int, element: T) = _data(index) = element
  def apply(index: Int): Option[T] = get(index)
  def get(index: Int): Option[T] =
    if(0 <= index && index < _size) {
      Some(_data(index))
    } else None

  def remove(index: Int): Option[T] =
    if (0 <= index && index < _size) {
      val o: T = _data(index)
      _size -= 1
      _data(index) = _data(_size)
      _data(_size) = null.asInstanceOf[T]
      Some(o)
    } else None

  def removeLast(): Option[T] =
    if (_size > 0) {
      _size -= 1
      val o = _data(_size)
      _data(_size) = null.asInstanceOf[T]
      Some(o)
    } else {
      None
    }

  def remove(o: T): Boolean = {
    var i = _size-1
    while(i >= 0) {
      if (o == _data(i)) {
        _size -= 1
        _data(i) = _data(_size)
        _data(_size) = null.asInstanceOf[T]
        return true
      }
      i -= 1
    }
    false
  }

  def removeAll(toRemove: MutableBag[T]): Boolean = {
    val removals = for(index <- 0 to size-1) yield toRemove.get(index) match {
      case Some(e) => remove(e)
      case None => false
    }
    removals.contains(true)
  }

  def contains(o: T): Boolean = {
    for(e <- _data) {
      if (e == o) return true
    }
    false
  }

  def clear(): Unit = {
    var index = 0
    while(index < _size) {
      _data(index) = null.asInstanceOf[T]
      index += 1
    }
    _size = 0
  }

  override def iterator: Iterator[T] = Iterator(_data.slice(0, _size):_*)

  private def grow():Unit = grow((_data.length * 3) / 2 + 1)
  private def grow(capacity: Int):Unit = {
    val oldData = _data
    _data = Array.ofDim(capacity)
    Array.copy(oldData, 0, _data, 0, oldData.length)
  }
}

object MutableBag {
  def apply[T:ClassTag](args:T*): MutableBag[T] = {
    val bag = new MutableBag[T]
    bag.addAll(args)
    bag
  }
}
