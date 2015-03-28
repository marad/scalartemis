package marad.scalartemis.core

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
  val typeId = componentType.id // TODO: remove this
}
