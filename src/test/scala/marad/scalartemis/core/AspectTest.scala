package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class AspectTest extends WordSpec with Matchers with BDD {
  class C1 extends Component
  class C2 extends Component
  class C3 extends Component

  val world = new World
  val entityC1C2   = world.createEntity(new C1, new C2)
  val entityC1     = world.createEntity(new C1)
  val entityC3     = world.createEntity(new C3)
  val entity       = world.createEntity()
  val entityC1C2C3 = world.createEntity(new C1, new C2, new C3)

  "AspectAll" should {
    "include only entities with all required components (or more)" in {
      When
      val aspectAll = Aspect.forAll(classOf[C1], classOf[C2])

      Then
      aspectAll ~ entityC1C2   shouldBe true
      aspectAll ~ entityC1     shouldBe false
      aspectAll ~ entityC3     shouldBe false
      aspectAll ~ entity       shouldBe false
      aspectAll ~ entityC1C2C3 shouldBe true
    }
  }

  "AspectOneOff" should {
    "include entities with one of required components" in {
      When
      val aspectOneOf = Aspect.forOneOf(classOf[C1], classOf[C2])

      Then
      aspectOneOf ~ entityC1C2   shouldBe true
      aspectOneOf ~ entityC1     shouldBe true
      aspectOneOf ~ entityC3     shouldBe false
      aspectOneOf ~ entity       shouldBe false
      aspectOneOf ~ entityC1C2C3 shouldBe true
    }
  }

  "AspectOnly" should {
    "include only entities with all required components (no more)" in {
      When
      val aspectOnly = Aspect.onlyFor(classOf[C1], classOf[C2])

      Then
      aspectOnly ~ entityC1C2   shouldBe true
      aspectOnly ~ entityC1     shouldBe false
      aspectOnly ~ entityC3     shouldBe false
      aspectOnly ~ entity       shouldBe false
      aspectOnly ~ entityC1C2C3 shouldBe false
    }
  }
}
