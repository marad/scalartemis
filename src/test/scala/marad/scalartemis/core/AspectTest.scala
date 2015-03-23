package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class AspectTest extends WordSpec with Matchers with BDD {

  "AspectAll" should {
    "include only entities with all aspects" in {
      When
      object C1 extends Component
      object C2 extends Component
      val world = new World
      val e = world.createEntity()
      e.addComponent(C1)
      e.addComponent(C2)
      val aspect = new AspectAll(C1.componentType, C2.componentType)
    }

//    "create aspect from component type" in {
//      When
//      val aspect = Aspect(new ComponentType(3))
//
//      Then
//      aspect.value shouldBe 8
//    }
//
//    "create aspect from multiple component types" in {
//      When
//      val types = List(new ComponentType(0), new ComponentType(2), new ComponentType(3))
//      val aspect = Aspect(types:_*)
//
//      Then
//      aspect ~ types(0) shouldBe true
//      aspect ~ types(1) shouldBe true
//      aspect ~ types(2) shouldBe true
//    }
//
//    "check if it matches component type" in {
//      Given
//      val componentType = new ComponentType(3)
//      val aspect = Aspect(8)
//
//      Expect
//      aspect ~ componentType shouldBe true
//    }
  }
}
