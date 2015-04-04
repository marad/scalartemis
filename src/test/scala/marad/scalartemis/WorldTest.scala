package marad.scalartemis

import marad.scalartemis.exception.ComponentNotFoundException
import org.scalatest.mock.MockitoSugar
import org.scalatest.{OptionValues, Matchers, WordSpec}
import org.mockito.Mockito.{when, verify}


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

//  "Entity Sets" should {
//    object C1 extends Component
//    class EntitySetsImpl extends World.EntitySets with World.ComponentManagement
//
//    "create entity sets and add entities to them" in {
//      Given
//      val impl = new EntitySetsImpl
//
//      When
//      impl.
//    }
//
//  }

  "Entity Manager" should {
    object C1 extends Component
    object C2 extends Component
    class EntityMgrImpl
      extends World.EntityManagement
      with World.EntitySets
      with World.ComponentManagement

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

    "create aspected entity lists for registered entity systems" in {
      Given
      object C1 extends Component
      val mgr = new EntityMgrImpl with World.Systems
      val aspect = Aspect.forAll(C1.getClass)
      val entitySystem = mock[EntitySystem]
      when(entitySystem.aspect) thenReturn aspect
      mgr.registerSystem(entitySystem)

      When
      val e1 = mgr.createEntity(C1)
      val e2 = mgr.createEntity()

      Then
      val entities = mgr.getEntitySet(aspect).entities
      entities.contains(e1) shouldBe true
      entities.contains(e2) shouldBe false
    }

    "create aspected entity lists from existing entities" in {
      Given
      object C1 extends Component
      val mgr = new EntityMgrImpl with World.Systems
      val aspect = Aspect.forAll(C1.getClass)
      val e1 = mgr.createEntity(C1)
      val e2 = mgr.createEntity()
      val entitySystem = mock[EntitySystem]
      when(entitySystem.aspect) thenReturn aspect

      When
      mgr.registerSystem(entitySystem)

      Then
      val entities = mgr.getEntitySet(aspect).entities
      entities.contains(e1) shouldBe true
      entities.contains(e2) shouldBe false
    }
  }

  "Entity Systems" should {
    class SystemsMgrImpl
      extends World.Systems
      with World.EntityManagement
      with World.EntitySets
      with World.ComponentManagement
    object S1 extends EntitySystem(Aspect.any) {
      override def process(delta: Float): Unit = ???
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

  "World" should {
    "run tasks and process entity systems on update" in {
      Given
      val S1 = mock[EntitySystem]
      when(S1.aspect) thenReturn Aspect.any
      val world = new World
      world.registerSystem(S1)

      When
      world.update(1f)

      Then
      verify(S1).process(1f)
    }
  }
}
