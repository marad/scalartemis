package io.github.marad.scalartemis

import io.github.marad.scalartemis.core.ComponentTypeManager

class Component {
  val componentType = ComponentTypeManager.getTypeFor(this.getClass)
}
