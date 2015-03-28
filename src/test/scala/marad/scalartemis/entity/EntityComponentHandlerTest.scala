package marad.scalartemis.entity

import marad.scalartemis.{BDD, Component}
import org.scalatest.{OptionValues, Matchers, WordSpec}

class EntityComponentHandlerTest extends WordSpec with Matchers with BDD with OptionValues {

  object C1 extends Component
  object C2 extends Component

  "Entity Component Handler" should {
    "register components in manager and allow querying by component type" in {
      Given
      val mgr = new EntityComponentHandler

      When
      mgr.addComponent(0, C1)
      mgr.addComponent(0, C2)
      mgr.addComponent(1, C2)

      Then
      mgr.getComponent(0, C1.getClass).value shouldBe C1
      mgr.getComponent(0, C2.getClass).value shouldBe C2
      mgr.getComponent(1, C2.getClass).value shouldBe C2

      mgr.getComponent(100, C1.getClass) shouldBe None
      mgr.getComponent(1, C1.getClass) shouldBe None
    }

    "reset entity components" in {
      Given
      val mgr = new EntityComponentHandler
      mgr.addComponent(0, C1)
      mgr.addComponent(0, C2)
      mgr.addComponent(1, C2)

      When
      mgr.resetEntity(0)

      Then
      mgr.getComponent(0, C1.getClass) shouldBe None
      mgr.getComponent(1, C2.getClass) shouldBe Some(C2)
    }

    "remove components from entity" in {
      val mgr = new EntityComponentHandler
      mgr.addComponent(0, C1)
      mgr.addComponent(0, C2)

      When
      mgr.removeComponent(0, C1.componentType)

      Then
      mgr.getComponent(0, C1.getClass) shouldBe None
      mgr.getComponent(0, C2.getClass) shouldBe Some(C2)
    }
  }
}
