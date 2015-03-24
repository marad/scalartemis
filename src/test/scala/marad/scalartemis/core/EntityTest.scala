package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class EntityTest extends WordSpec with Matchers with BDD {
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
  }
}
