package marad.scalartemis.core

import marad.scalartemis.BDD
import org.mockito.Mockito.verify
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class EntityTest extends WordSpec with Matchers with BDD with MockitoSugar {
  class C1 extends Component
  class C2 extends Component
  class C3 extends Component

  val (c1, c2, c3) = (new C1, new C2, new C3)
  val (c1Type, c2Type, c3Type) = (
    ComponentTypeManager.getTypeFor(classOf[C1]).id,
    ComponentTypeManager.getTypeFor(classOf[C2]).id,
    ComponentTypeManager.getTypeFor(classOf[C3]).id)
  val world = new World

  "Entity" should {
    "register proper component types" in {
      Given
      val entity = world.createEntity()

      When
      entity.addComponent(c1)
      entity.addComponent(c3)

      Then
      val types = entity.componentTypes
      types.contains(c1Type) shouldBe true
      types.contains(c2Type) shouldBe false
      types.contains(c3Type) shouldBe true
    }

    "notify world about added component" in {
      Given
      val worldMock = mock[World]
      val entity = new Entity(worldMock, 0)

      When
      entity.addComponent(c1)

      Then
      verify(worldMock).componentAdded(entity, c1)
    }
  }
}
