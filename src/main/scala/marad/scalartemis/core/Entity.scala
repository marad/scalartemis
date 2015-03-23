package marad.scalartemis.core

import scala.collection.mutable

class Entity(val world: World, val id: Int) {
  private var _active = false
  private val componentTypes = new mutable.BitSet()

  def activate(): Unit = {
    _active = true
  }

  def deactivate(): Unit = {
    _active = false
  }

  def addComponent(component: Component): Unit = {
    componentTypes.add(component.componentType.id)
    world.componentAdded(this, component)
  }
  def removeComponent(component: Component): Unit = {
    componentTypes.remove(component.componentType.id)
    world.componentRemoved(this, component)
  }
}
