package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class EntityComponentManagerTest extends WordSpec with Matchers with BDD {

  object C1 extends Component
  object C2 extends Component

  "Entity Component Manager" should {
    "register components for entities and allow to check the aspects" in {
      Given
      val aspect = Aspect.forAll(C1.getClass, C2.getClass)
      val mgr = new EntityComponentManager

      When
      mgr.addComponent(0, C1)
      mgr.addComponent(0, C2)
      mgr.addComponent(1, C2)

      Then
      mgr.checkAspect(0, aspect) shouldBe true
      mgr.checkAspect(1, aspect) shouldBe false
    }

    "query for components" in {
      Given
      val aspect = Aspect.forAll(C1.getClass, C2.getClass)
      val mgr = new EntityComponentManager

      When
      mgr.addComponent(0, C1)
      mgr.addComponent(0, C2)
      mgr.addComponent(1, C2)

      Then
      mgr.getComponent(0, C1.componentType) shouldBe C1
      mgr.getComponent(0, C2.componentType) shouldBe C2
      mgr.getComponent(1, C2.componentType) shouldBe C2

    }
  }
}
