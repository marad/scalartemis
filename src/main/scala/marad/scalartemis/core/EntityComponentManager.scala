package marad.scalartemis.core

import marad.scalartemis.core.utils.DynamicArray

import scala.collection.mutable

class EntityComponentManager {
  private val componentTypes = new DynamicArray[mutable.BitSet]()
  private val components = new DynamicArray[DynamicArray[Component]]()

  def addComponent(entityId: Int, component: Component): Unit = {
    ensureEntityDefined(entityId)
    componentTypes(entityId).add(component.typeId)
    components(entityId)(component.typeId) = component
  }

  def removeComponent(entityId: Int, component: Component): Unit = ???

  def getComponent(entityId: Int, componentType: ComponentType): Component =
    components(entityId)(componentType.id)
  
  def checkAspect(entityId: Int, aspect: Aspect): Boolean =
    aspect ~ componentTypes(entityId)

  private def ensureEntityDefined(entityId: Int): Unit = {
    if (!componentTypes.defined(entityId)) {
      componentTypes(entityId) = new mutable.BitSet()
    }
    if (!components.defined(entityId)) {
      components(entityId) = new DynamicArray[Component]()
    }
  }
}
