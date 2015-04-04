package marad.scalartemis.core.utils

import marad.scalartemis.BDD
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class DynamicArrayTest extends WordSpec with Matchers with BDD with PropertyChecks {

  "Dynamic Array" should {
    "store values at random indexes" in {
      val indexSet = Gen.choose(0, 100)
      forAll(indexSet, indexSet, indexSet) { (a, b, c) =>
        whenever(a != b && b != c && a != c) {
          Given
          val array = new DynamicArray[Int]

          When
          array(a) = a
          array(b) = b
          array(c) = c

          Then
          array(a) shouldBe a
          array(b) shouldBe b
          array(c) shouldBe c
        }
      }
    }

    "optimize the array" in {
      for(min <- 0 to 100; max <- 101 to 300) {
        Given
        val diff = max - min
        val unoptimized = new DynamicArray[Int]
        unoptimized(max) = max
        unoptimized(min) = min
        unoptimized.remove(min)
        assume(unoptimized.internalArrayLength >= diff)

        When
        val sizeBefore = unoptimized.internalArrayLength
        unoptimized.optimize()

        Then
        (sizeBefore - unoptimized.internalArrayLength) shouldBe diff
        unoptimized(max) shouldBe max
      }
    }

    "fail if trying to access not existing array elements" in {
      Given
      val array = new DynamicArray[Int]
      array(100) = 100
      array(105) = 105

      When
      val ex = intercept[ArrayIndexOutOfBoundsException] {
        array(10)
      }

      Then
      ex shouldNot be (null)
      ex.getMessage shouldBe "10"
    }

    "be able to determine that value is defined at given location" in {
      Given
      val array = new DynamicArray[Int]

      When
      array(100) = 100
      array(105) = 105

      Then
      array.defined(90) shouldBe false
      array.defined(100) shouldBe true
      array.defined(102) shouldBe false
      array.defined(105) shouldBe true
      array.defined(110) shouldBe false
    }
  }
}
