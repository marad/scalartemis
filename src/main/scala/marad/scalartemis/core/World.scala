package marad.scalartemis.core

import marad.scalartemis.core.entity.{EntityComponentHandler, EntityAspectHandler, EntityManager, Entity}
import marad.scalartemis.core.exception.ComponentNotFoundException
import marad.scalartemis.core.utils.{Bag, MutableBag}

class World
    extends World.ComponentManagement
    with World.EntityManagement
    with World.Systems
    with World.Tasks {
  def update(delta: Float): Unit = {
    runTasks(delta)
    updateSystems(entities, delta)
  }
}

object World {

  /**
   * Manages entity components
   */
  trait ComponentManagement {
    protected val entityComponentHandler = new EntityComponentHandler
    protected val entityAspectHandler = new EntityAspectHandler

    def addComponent(entity: Entity, component: Component): Unit = {
      entityAspectHandler.addType(entity.id, component)
      entityComponentHandler.addComponent(entity.id, component)
    }

    def removeComponent(entity: Entity, componentType: ComponentType): Unit = {
      if(!entityAspectHandler.hasType(entity.id, componentType))
        throw new ComponentNotFoundException

      entityAspectHandler.removeType(entity.id, componentType)
      entityComponentHandler.removeComponent(entity.id, componentType)
    }

    def getComponent(entity: Entity, componentType: ComponentType): Option[Component] =
      entityComponentHandler.getComponent(entity.id, componentType)

    protected def resetComponents(entity: Entity): Unit = {
      entityComponentHandler.resetEntity(entity.id)
      entityAspectHandler.resetEntity(entity.id)
    }
  }

  /**
   * Manages entity creation and removal
   */
  trait EntityManagement { this : ComponentManagement =>
    protected val entityManager = new EntityManager

    def entities: Bag[Entity] = entityManager.entities

    def createEntity(components: Component*): Entity = {
      val entity = entityManager.acquireEntity()
      components.foreach(addComponent(entity, _))
      entity
    }

    def destroyEntity(entity: Entity): Unit = {
      resetComponents(entity)
      entityManager.releaseEntity(entity.id)
    }
  }

  /**
   * Manages entity systems
   */
  trait Systems { this : ComponentManagement =>
    protected val _systems = new MutableBag[EntitySystem]

    def systems: Bag[EntitySystem] = _systems
    
    def registerSystem(entitySystem: EntitySystem): Unit =
      _systems.add(entitySystem)

    protected def updateSystems(entities: Bag[Entity], delta: Float): Unit =
      _systems.foreach { s =>
        entities.foreach { e =>
          if (entityAspectHandler.checkAspect(e.id, s.aspect)) {
            s.process(e, delta)
          }
        }
      }
  }

  /**
   * Manages custom tasks
   */
  trait Tasks {
    protected val _tasks = new MutableBag[UpdateTask]

    def tasks: Bag[UpdateTask] = _tasks

    def registerTask(updateTask: UpdateTask): Unit =
      _tasks.add(updateTask)

    protected def runTasks(delta: Float) = _tasks.foreach(_.update(delta))
  }
}
