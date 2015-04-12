package marad.scalartemis

import marad.scalartemis.core.{EntityManager, EntityComponentHandler, EntityAspectHandler, ComponentType}
import marad.scalartemis.exception.ComponentNotFoundException
import marad.scalartemis.core.utils.{EntitySet, Bag, MutableBag}

import scala.collection.mutable

class World
    extends World.AspectManagement
    with World.ComponentManagement
    with World.EntityManagement
    with World.EntitySets
    with World.Systems {
  def update(delta: Float): Unit = updateSystems(delta)
}

object World {

  trait AspectManagement {
    protected val entityAspectHandler = new EntityAspectHandler
  }

  /**
   * Manages entity components
   */
  trait ComponentManagement { this : AspectManagement =>
    protected val entityComponentHandler = new EntityComponentHandler

    def addComponent(entity: Entity, component: Component): Unit = {
      // TODO: should update the entity sets if entity has now new aspect
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
  trait EntityManagement { this : ComponentManagement with EntitySets =>
    protected val entityManager = new EntityManager

    def entities: Bag[Entity] = entityManager.entities

    def createEntity(components: Component*): Entity = {
      val entity = entityManager.acquireEntity()
      components.foreach(addComponent(entity, _))
      addEntityToSets(entity)
      entity
    }

    def destroyEntity(entity: Entity): Unit = {
      resetComponents(entity)
      removeEntityFromSets(entity)
      entityManager.releaseEntity(entity.id)
    }
  }

  /**
   * Manages entity sets
   */
  trait EntitySets { this : AspectManagement with EntityManagement =>
    protected val _entitySets = new mutable.HashMap[Aspect, EntitySet]

    def getEntitySet(aspect: Aspect): EntitySet =
      _entitySets.getOrElseUpdate(aspect, {
        val set = new EntitySet
        entities.foreach(addToSetIfAspectMatches(_, aspect, set))
        set
      })

    protected def addEntityToSets(entity: Entity): Unit =
      _entitySets.foreach { case (aspect, set) =>
        addToSetIfAspectMatches(entity, aspect, set)
      }

    protected def removeEntityFromSets(entity: Entity): Unit =
      _entitySets.foreach { case (aspect, set) =>
        entityAspectHandler.ifHasAspect(entity.id, aspect) {
          set.removeEntity(entity)
        }
      }

    private def addToSetIfAspectMatches(entity: Entity, aspect: Aspect, entitySet: EntitySet): Unit =
      entityAspectHandler.ifHasAspect(entity.id, aspect) {
        entitySet.addEntity(entity)
      }
  }

  /**
   * Manages entity systems
   */
  trait Systems { this : EntitySets =>
    protected val _systems = new MutableBag[EntitySystem]

    def systems: Bag[EntitySystem] = _systems
    
    def registerSystem(entitySystem: EntitySystem): Unit = {
      entitySystem.onRegister(getEntitySet(entitySystem.aspect))
      _systems.add(entitySystem)
    }

    protected def updateSystems(delta: Float): Unit =
      _systems.foreach(_.process(delta))
  }
}
