package marad.scalartemis.core

import marad.scalartemis.{Aspect, BDD, Component}
import org.scalatest.{Matchers, WordSpec}

class EntityAspectHandlerTest extends WordSpec with Matchers with BDD {
  object C1 extends Component
  object C2 extends Component

  "Entity Aspect Handler" should {
    "keep track of entity aspect" in {
      Given
      val handler = new EntityAspectHandler

      When
      handler.addType(0, C1)
      handler.addType(0, C2)

      Then
      handler.checkAspect(0, Aspect.forAll(C1.getClass, C2.getClass)) shouldBe true
    }

    "checking aspect should return false for undefined component types" in {
      Given
      val handler = new EntityAspectHandler

      When
      val result = handler.checkAspect(1234, Aspect.any)

      Then
      result shouldBe false
    }


    "remove aspects from entity" in {
      Given
      val aspectAll = Aspect.forAll(C1.getClass, C2.getClass)
      val aspectC1 = Aspect.forOneOf(C1.getClass)
      val aspectC2 = Aspect.forOneOf(C2.getClass)
      val handler = new EntityAspectHandler
      handler.addType(0, C1)
      handler.addType(0, C2)

      When
      handler.removeType(0, C1.getClass)

      Then
      handler.checkAspect(0, aspectAll) shouldBe false
      handler.checkAspect(0, aspectC1) shouldBe false
      handler.checkAspect(0, aspectC2) shouldBe true
    }

    "not fail if trying to remove non-existing component" in {
      Given
      val handler = new EntityAspectHandler

      When
      handler.removeType(0, C1.getClass)
    }

    "check if entity has given component type" in {
      Given
      val handler = new EntityAspectHandler

      When
      handler.addType(0, C1)

      Then
      handler.hasType(0, C1.componentType) shouldBe true
      handler.hasType(0, C2.componentType) shouldBe false
      handler.hasType(1, C1.componentType) shouldBe false
    }

    "reset entity" in {
      Given
      val handler = new EntityAspectHandler
      handler.addType(0, C1)
      handler.addType(0, C2)
      handler.addType(1, C1)

      When
      handler.resetEntity(0)

      Then
      handler.hasType(0, C1.componentType) shouldBe false
      handler.hasType(0, C2.componentType) shouldBe false
      handler.hasType(1, C1.componentType) shouldBe true
    }
  }
}
