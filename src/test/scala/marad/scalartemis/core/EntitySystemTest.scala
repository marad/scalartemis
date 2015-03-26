package marad.scalartemis.core

import marad.scalartemis.BDD
import marad.scalartemis.core.utils.MutableBag
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import org.mockito.Mockito.when

class EntitySystemTest extends WordSpec with Matchers with BDD with MockitoSugar {
  class C1 extends Component
  class C2 extends Component

  class TestEntitySystem(aspect: Aspect) extends EntitySystem(aspect) {
    override def process(entities: Iterable[Entity]): Unit = ???
  }

  val (c1, c2) = (new C1, new C2)
  val world = mock[World]
  val entityC1   = new Entity(world, 0)
  entityC1.addComponent(c1)
  val entityC1C2 = new Entity(world, 1)
  entityC1C2.addComponent(c1)
  entityC1C2.addComponent(c2)

  when(world.entities) thenReturn MutableBag(entityC1, entityC1C2)

  "Entity System" should {
    "start processing entities on update" in {
      Given
      val aspect = Aspect.forOneOf(classOf[C1], classOf[C2])
      val entitySystemUnderTest = new TestEntitySystem(aspect)
      entitySystemUnderTest.onRegister(world)

      Expect
      intercept[NotImplementedError] {
        entitySystemUnderTest.update()
      }
    }
  }
}
