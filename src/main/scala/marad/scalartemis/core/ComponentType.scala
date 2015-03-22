package marad.scalartemis.core

case class ComponentType(bit: Long = ComponentType.nextBit,
                         id: Int = ComponentType.nextId)

object ComponentType {
  private var _nextBit: Long = 1l
  private var _nextId: Int = 0

  def nextBit = {
    val bit = _nextBit
    _nextBit = _nextBit << 1
    bit
  }

  def nextId = {
    val id = _nextId
    _nextId += 1
    id
  }
}
