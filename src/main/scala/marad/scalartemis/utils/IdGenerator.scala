package marad.scalartemis.utils

class IdGenerator {
  private var _nextId = -1
  def nextId:Int = {
    _nextId += 1
    _nextId
  }
}
