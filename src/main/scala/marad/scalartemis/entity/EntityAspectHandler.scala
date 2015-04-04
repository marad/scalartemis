package marad.scalartemis.entity

import marad.scalartemis.{ComponentTypeManager, Aspect, Component, ComponentType}
import ComponentTypeManager.ComponentClass
import marad.scalartemis.{Aspect, Component, ComponentType}
import marad.scalartemis.utils.DynamicArray

import scala.collection.mutable

class EntityAspectHandler {
  private val componentTypes = new DynamicArray[mutable.BitSet]()

  def addType(entityId: Int, component: Component): Unit = addType(entityId, component.componentType)

  def addType(entityId: Int, componentType: ComponentType): Unit = {
    ensureEntityDefined(entityId)
    componentTypes(entityId).add(componentType.id)
  }

  def removeType(entityId: Int, componentClass: ComponentClass): Unit =
    removeType(entityId, ComponentTypeManager.getTypeFor(componentClass))

  def removeType(entityId: Int, componentType: ComponentType): Unit = {
    ensureEntityDefined(entityId)
    componentTypes(entityId).remove(componentType.id)
  }

  def checkAspect(entityId: Int, aspect: Aspect): Boolean =
    componentTypes.defined(entityId) && aspect ~ componentTypes(entityId)

  def hasType(entityId: Int, componentType: ComponentType): Boolean =
    componentTypes.defined(entityId) && componentTypes(entityId).contains(componentType.id)

  def ifHasAspect(entityId: Int, aspect: Aspect)(op: => Unit): Unit = if (checkAspect(entityId, aspect)) op

  def ifHasType(entityId: Int, componentType: ComponentType)(op: => Unit): Unit = if (hasType(entityId, componentType)) op

  def resetEntity(entityId: Int): Unit =
    componentTypes(entityId) = new mutable.BitSet()

  private def ensureEntityDefined(entityId: Int): Unit = {
    if (!componentTypes.defined(entityId)) {
      componentTypes(entityId) = new mutable.BitSet()
    }
  }
}
