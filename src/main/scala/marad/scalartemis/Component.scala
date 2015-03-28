package marad.scalartemis

import marad.scalartemis.entity.ComponentTypeManager

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
}
