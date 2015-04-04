package marad.scalartemis

import marad.scalartemis.core.ComponentTypeManager

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
}
