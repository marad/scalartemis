package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class AspectTest extends WordSpec with Matchers with BDD with MockitoSugar {
  class C1 extends Component
  class C2 extends Component
  class C3 extends Component

  val world = mock[World]
  val entity       = new EntityBuilder().withWorld(world).withId(3).build()
  val entityC1     = new EntityBuilder().withWorld(world).withId(1).withComponents(new C1).build()
  val entityC3     = new EntityBuilder().withWorld(world).withId(2).withComponents(new C3).build()
  val entityC1C2   = new EntityBuilder().withWorld(world).withId(0).withComponents(new C1, new C2).build()
  val entityC1C2C3 = new EntityBuilder().withWorld(world).withId(4).withComponents(new C1, new C2, new C3).build()

  "AspectAll" should {
    "include only entities with all required components (or more)" in {
      When
      val aspectAll = Aspect.forAll(classOf[C1], classOf[C2])

      Then
      aspectAll ~ entity       shouldBe false
      aspectAll ~ entityC1     shouldBe false
      aspectAll ~ entityC3     shouldBe false
      aspectAll ~ entityC1C2   shouldBe true
      aspectAll ~ entityC1C2C3 shouldBe true
    }
  }

  "AspectOneOff" should {
    "include entities with one of required components" in {
      When
      val aspectOneOf = Aspect.forOneOf(classOf[C1], classOf[C2])

      Then
      aspectOneOf ~ entity       shouldBe false
      aspectOneOf ~ entityC1     shouldBe true
      aspectOneOf ~ entityC3     shouldBe false
      aspectOneOf ~ entityC1C2   shouldBe true
      aspectOneOf ~ entityC1C2C3 shouldBe true
    }
  }

  "AspectOnly" should {
    "include only entities with all required components (no more)" in {
      When
      val aspectOnly = Aspect.onlyFor(classOf[C1], classOf[C2])

      Then
      aspectOnly ~ entity       shouldBe false
      aspectOnly ~ entityC1     shouldBe false
      aspectOnly ~ entityC3     shouldBe false
      aspectOnly ~ entityC1C2   shouldBe true
      aspectOnly ~ entityC1C2C3 shouldBe false
    }
  }
}
