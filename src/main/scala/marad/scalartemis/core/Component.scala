package marad.scalartemis.core

class Component {
  val typeId = ComponentTypeManager.getTypeFor(this.getClass).id
}
