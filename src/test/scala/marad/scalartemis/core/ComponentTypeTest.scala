package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class ComponentTypeTest extends WordSpec with BDD with Matchers {
  "Component type" should {
    "calculate bit value" in {
      Given
      val componentType = new ComponentType(3)

      Expect
      componentType.bit shouldBe 8
    }
  }
}
