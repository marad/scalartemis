package marad.scalartemis

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
}
