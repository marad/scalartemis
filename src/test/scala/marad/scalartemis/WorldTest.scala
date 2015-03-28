package marad.scalartemis

import marad.scalartemis.entity.Entity
import marad.scalartemis.exception.ComponentNotFoundException
import org.scalatest.mock.MockitoSugar
import org.scalatest.{OptionValues, Matchers, WordSpec}
import org.mockito.Mockito.{when, verify, times}


class WorldTest extends WordSpec with Matchers with BDD with OptionValues with MockitoSugar {
  "Component Management" should {
    object C1 extends Component
    object C2 extends Component

    class ComponentMgrImpl extends World.ComponentManagement

    "register components for entities" in {
      Given
      val mgr = new ComponentMgrImpl
      val entity = new Entity(0)

      When
      mgr.addComponent(entity, C1)

      Then
      mgr.getComponent(entity, C1.componentType).value shouldBe C1
    }

    "remove components from entities by component type" in {
      Given
      val mgr = new ComponentMgrImpl
      val entity = new Entity(0)
      mgr.addComponent(entity, C1)
      mgr.addComponent(entity, C2)

      When
      mgr.removeComponent(entity, C1.componentType)

      Then
      mgr.getComponent(entity, C1.componentType) shouldBe None
      mgr.getComponent(entity, C2.componentType) shouldBe Some(C2)
    }

    "throw exception when trying to remove not-existing component" in {
      Given
      val mgr = new ComponentMgrImpl
      val entity = new Entity(0)

      When
      val ex = intercept[ComponentNotFoundException] {
        mgr.removeComponent(entity, C1.componentType)
      }

      Then
      ex shouldNot be (null)
    }
  }

  "Entity Manager" should {
    object C1 extends Component
    object C2 extends Component
    class EntityMgrImpl extends World.EntityManagement with World.ComponentManagement

    "create entities with components" in {
      Given
      val mgr = new EntityMgrImpl

      When
      val entity = mgr.createEntity(C1, C2)

      Then
      mgr.entities should have size 1
      mgr.getComponent(entity, C1.componentType) shouldBe Some(C1)
    }

    "list all entities" in {
      Given
      val mgr = new EntityMgrImpl

      When
      mgr.createEntity()
      mgr.createEntity()
      mgr.createEntity()

      Then
      mgr.entities should have size 3
    }

    "release entities" in {
      Given
      val mgr = new EntityMgrImpl
      val entity = mgr.createEntity(C1)

      When
      mgr.destroyEntity(entity)

      Then
      mgr.entities shouldBe empty
      mgr.getComponent(entity, C1.componentType) shouldBe None
    }
  }

  "Entity Systems" should {
    class SystemsMgrImpl extends World.Systems with World.ComponentManagement
    object S1 extends EntitySystem(Aspect.any) {
      override def process(entity: Entity, delta: Float): Unit = ???
    }

    "register system" in {
      Given
      val mgr = new SystemsMgrImpl

      When
      mgr.registerSystem(S1)

      Then
      mgr.systems should have size 1
    }
  }

  "Tasks" should {
    class TasksMgrImpl extends World.Tasks
    object T1 extends UpdateTask {
      override def update(delta: Float): Unit = ???
    }

    "register task" in {
      Given
      val mgr = new TasksMgrImpl

      When
      mgr.registerTask(T1)

      Then
      mgr.tasks should have size 1
    }
  }

  "World" should {
    "run tasks and process entity systems on update" in {
      Given
      object C1 extends Component
      object C2 extends Component
      val S1 = mock[EntitySystem]
      val S2 = mock[EntitySystem]
      val T1 = mock[UpdateTask]
      val world = new World
      val e1 = world.createEntity(C1, C2)
      val e2 = world.createEntity(C1)
      world.registerSystem(S1)
      world.registerSystem(S2)
      world.registerTask(T1)

      when(S1.aspect) thenReturn Aspect.any
      when(S2.aspect) thenReturn Aspect.onlyFor(C1.getClass)

      When
      world.update(1f)

      Then
      verify(S1).process(e1, 1f)
      verify(S1).process(e2, 1f)
      verify(S2, times(0)).process(e1, 1f)
      verify(S2).process(e2, 1f)
      verify(T1).update(1f)
    }
  }
}
