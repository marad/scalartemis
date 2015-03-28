package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class ComponentMappingTest extends WordSpec with Matchers with BDD {

  class TestImpl(val world: World) extends ComponentMapping

  "Component Mapping" should {
    "get entity component from world" in {
      Given
      class C1 extends Component
      val world = new World
      val c1 = new C1
      val entity = world.createEntity(c1)
      val underTest = new TestImpl(world)

      When
      val result = underTest.component[C1](entity)

      Then
      result shouldBe Some(c1)
    }
  }
}
