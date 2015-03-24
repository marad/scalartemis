package marad.scalartemis.core

import marad.scalartemis.BDD
import marad.scalartemis.core.utils.Bag
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class EntitySystemTest extends WordSpec with Matchers with BDD with MockitoSugar {
  class C1 extends Component
  class C2 extends Component

  class TestEntitySystem(aspect: Aspect) extends EntitySystem(aspect) {
    override def process(entities: Bag[Entity]): Unit = ???
  }

  val (c1, c2) = (new C1, new C2)
  val world = mock[World]
  val entityC1   = new Entity(world, 0)
  entityC1.addComponent(c1)
  val entityC1C2 = new Entity(world, 1)
  entityC1C2.addComponent(c1)
  entityC1C2.addComponent(c2)

  "Entity System" should {
    "register only entities with given aspects" in {
      Given
      val aspect = Aspect.onlyFor(classOf[C1], classOf[C2])
      val entitySystemUnderTest = new TestEntitySystem(aspect)

      When
      entitySystemUnderTest.entityCreated(entityC1)
      entitySystemUnderTest.entityCreated(entityC1C2)

      Then
      entitySystemUnderTest.entities.contains(entityC1) shouldBe false
      entitySystemUnderTest.entities.contains(entityC1C2) shouldBe true
    }

    "remove entities from list" in {
      Given
      val aspect = Aspect.forOneOf(classOf[C1], classOf[C2])
      val entitySystemUnderTest = new TestEntitySystem(aspect)
      entitySystemUnderTest.entityCreated(entityC1)
      entitySystemUnderTest.entityCreated(entityC1C2)
      assume(entitySystemUnderTest.entities.contains(entityC1))
      assume(entitySystemUnderTest.entities.contains(entityC1C2))

      When
      entitySystemUnderTest.entityDestroyed(entityC1C2)

      Then
      entitySystemUnderTest.entities.contains(entityC1) shouldBe true
      entitySystemUnderTest.entities.contains(entityC1C2) shouldBe false
    }

    "start processing entities on update" in {
      Given
      val aspect = Aspect.forOneOf(classOf[C1], classOf[C2])
      val entitySystemUnderTest = new TestEntitySystem(aspect)

      Expect
      intercept[NotImplementedError] {
        entitySystemUnderTest.update()
      }
    }

    "fetch world entities when registering" in {
      Given
      val aspect = Aspect.onlyFor(classOf[C1], classOf[C2])
      val entitySystemUnderTest = new TestEntitySystem(aspect)
      val entityBag = Bag(entityC1, entityC1C2)

      When
      entitySystemUnderTest.onRegister(entityBag)

      Then
      entitySystemUnderTest.entities.contains(entityC1) shouldBe false
      entitySystemUnderTest.entities.contains(entityC1C2) shouldBe true
    }
  }
}
