package marad.scalartemis.core

class ComponentMapper[T <: Component](val componentClass: Class[T]) {
  val componentType = ComponentTypeManager.getTypeFor(componentClass)
}
