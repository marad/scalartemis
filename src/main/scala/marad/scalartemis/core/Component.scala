package marad.scalartemis.core

import marad.scalartemis.core.entity.ComponentTypeManager

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
}
