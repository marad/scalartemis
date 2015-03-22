package marad.scalartemis.core

import scala.beans.BeanProperty

class Entity(val world: World, val id: Int) {
  var uniqueId: Long = 0l
  var typeBits: Long = 0l
  var systemBits: Long = 0l

  def reset():Unit = ???
}
