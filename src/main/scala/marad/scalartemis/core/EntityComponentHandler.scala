package marad.scalartemis.core

import marad.scalartemis.Component
import marad.scalartemis.core.ComponentTypeManager.ComponentClass
import marad.scalartemis.core.utils.DynamicArray

class EntityComponentHandler {
  private val components = new DynamicArray[DynamicArray[Component]]()

  def addComponent(entityId: Int, component: Component): Unit = {
    ensureEntityDefined(entityId)
    components(entityId)(component.componentType.id) = component
  }

  def removeComponent(entityId: Int, componentType: ComponentType): Unit = {
    ensureEntityDefined(entityId)
    components(entityId).remove(componentType.id)
  }

  def resetEntity(entityId: Int): Unit = components(entityId) = new DynamicArray[Component]()

  def getComponent(entityId: Int, componentType: ComponentType): Option[Component] =
    if (components.defined(entityId) && components(entityId).defined(componentType.id)) {
      Some(components(entityId)(componentType.id))
    } else None

  def getComponent(entityId: Int, componentClass: ComponentClass): Option[Component] =
    getComponent(entityId, ComponentTypeManager.getTypeFor(componentClass))
  
  private def ensureEntityDefined(entityId: Int): Unit =
    if (!components.defined(entityId)) {
      resetEntity(entityId)
    }
}
