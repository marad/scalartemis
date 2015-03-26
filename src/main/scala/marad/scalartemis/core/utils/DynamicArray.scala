package marad.scalartemis.core.utils

class DynamicArray[A] {
  protected def initialSize = 1
  protected var array: Array[AnyRef] = new Array[AnyRef](math.max(initialSize, 1))
  protected var minIndex: Int = Int.MaxValue
  protected def maxIndex: Int = minIndex + array.length

  def internalArrayLength = array.length

  def apply(idx: Int): A = {
    array(idx - minIndex).asInstanceOf[A]
  }

  def update(idx: Int, elem: A): Unit = {
    ensureOptimizedSizeFor(idx)
    array(idx - minIndex) = elem.asInstanceOf[AnyRef]
  }

  def remove(idx: Int): Unit = {
    update(idx, null.asInstanceOf[A])
  }

  def optimize() = {
    var i: Int = 0
    var continue: Boolean = true
    while(continue && i < array.length) {
      continue = if (array(i) == null) {
        i += 1
        true
      } else false
    }

    val newLength = array.length - i
    val newArray: Array[AnyRef] = new Array(newLength)
    scala.compat.Platform.arraycopy(array, i, newArray, 0, newLength)

    minIndex += i
    array = newArray
  }

  override def toString: String = "DynamicArray(" + array.mkString(", ") + ")"

  protected def determineSize(min: Int, max: Int): Int = 3 * (max - min) / 2 + 1

  protected def ensureOptimizedSizeFor(idx: Int) = {
    if (idx < minIndex || idx >= maxIndex) {
      val newMinIndex = if (minIndex > idx) idx else minIndex
      val newMaxIndex = if (maxIndex < idx) idx else maxIndex
      val positionInNewArray = if (minIndex != Int.MaxValue) minIndex - newMinIndex else 0
      val newSize = determineSize(newMinIndex, newMaxIndex)
      val newArray: Array[AnyRef] = new Array(newSize.toInt)
      scala.compat.Platform.arraycopy(array, 0, newArray, positionInNewArray, array.length)

      array = newArray
      minIndex = newMinIndex
    }
  }


}