package marad.scalartemis.core

import scala.collection.mutable

class Entity(val world: World, val id: Int) {
  private val _componentTypes = new mutable.BitSet()

  def addComponent(component: Component): Unit = {
    _componentTypes.add(component.typeId)
    world.componentAdded(this, component)
  }
  def removeComponent(component: Component): Unit = {
    _componentTypes.remove(component.typeId)
    world.componentRemoved(this, component)
  }

  def componentTypes = _componentTypes.clone()
}
