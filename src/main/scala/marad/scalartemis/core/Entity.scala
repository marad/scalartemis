package marad.scalartemis.core

class Entity(val world: World, val id: Int) {
  private var _active = false

  def activate(): Unit = {
    _active = true
  }

  def deactivate(): Unit = {
    _active = false
  }

  def addComponent(component: Component): Unit =
    world.componentAdded(this, component)
  def removeComponent(component: Component): Unit =
    world.componentRemoved(this, component)
}
