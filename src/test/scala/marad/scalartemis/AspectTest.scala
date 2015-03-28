package marad.scalartemis

import marad.scalartemis.TestApp.C1
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable

class AspectTest extends WordSpec with Matchers with BDD with MockitoSugar {
  class C1 extends Component
  class C2 extends Component
  class C3 extends Component

  val (c1, c2, c3) = (new C1, new C2, new C3)

  val emptySet = mutable.BitSet()
  val setC1 = mutable.BitSet(c1.componentType.id)
  val setC3 = mutable.BitSet(c3.componentType.id)
  val setC1C2 = mutable.BitSet(c1.componentType.id, c2.componentType.id)
  val setC1C2C3 = mutable.BitSet(c1.componentType.id, c2.componentType.id, c3.componentType.id)

  "AspectAll" should {
    "include only entities with all required components (or more)" in {
      When
      val aspectAll = Aspect.forAll[C1, C2]

      Then
      aspectAll ~ emptySet  shouldBe false
      aspectAll ~ setC1     shouldBe false
      aspectAll ~ setC3     shouldBe false
      aspectAll ~ setC1C2   shouldBe true
      aspectAll ~ setC1C2C3 shouldBe true
    }
  }

  "AspectOneOff" should {
    "include entities with one of required components" in {
      When
      val aspectOneOf = Aspect.forOneOf[C1, C2]

      Then
      aspectOneOf ~ emptySet  shouldBe false
      aspectOneOf ~ setC1     shouldBe true
      aspectOneOf ~ setC3     shouldBe false
      aspectOneOf ~ setC1C2   shouldBe true
      aspectOneOf ~ setC1C2C3 shouldBe true
    }
  }

  "AspectOnly" should {
    "include only entities with all required components (no more)" in {
      When
      val aspectOnly = Aspect.onlyFor[C1, C2]

      Then
      aspectOnly ~ emptySet  shouldBe false
      aspectOnly ~ setC1     shouldBe false
      aspectOnly ~ setC3     shouldBe false
      aspectOnly ~ setC1C2   shouldBe true
      aspectOnly ~ setC1C2C3 shouldBe false
    }
  }
}
