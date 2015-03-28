package marad.scalartemis.entity

class Entity(val id: Int) extends AnyVal {
  override def toString: String = "entity-" + id
}
