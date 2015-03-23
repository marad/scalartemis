package marad.scalartemis.core

class ComponentType(val id: Int) extends AnyVal {
  def bit: Long = 1 << id
}
