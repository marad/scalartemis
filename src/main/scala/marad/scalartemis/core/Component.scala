package marad.scalartemis.core

class Component {
  lazy val componentType: ComponentType = ComponentTypeManager.getTypeFor(this.getClass)
}
