package marad.scalartemis.core.utils

import org.scalatest.{Matchers, OptionValues, WordSpec}

class MutableBagTest extends WordSpec with OptionValues with Matchers {

  val createBag = new {
    def empty = new MutableBag[String]
    def simple = MutableBag("a", "b", "c")
  }

  "A Bag" should {
    "add single element" in {
      val bag = new MutableBag[String]
      bag.add("a")
      bag.size shouldBe 1
      bag(0).value shouldBe "a"
    }

    "add all elements and fetch elements by index" in {
      val bag = new MutableBag[String]
      bag.addAll(List("a", "b", "c"))
      bag(0).value shouldBe "a"
      bag(1).value shouldBe "b"
      bag(2).value shouldBe "c"
    }

    "allow updating elements" in {
      val bag = createBag.simple
      bag(1) = "z"
      bag(1).value shouldBe "z"
    }

    "grow if adding element when full" in {
      val bag = new MutableBag[String](2)
      bag.addAll(List("a", "b"))
      bag.capacity shouldBe 2
      bag.add("c")
      bag.capacity shouldBe > (2)
    }

    "return None when getting not existing element" in {
      val bag = createBag.simple
      bag(-1) shouldBe None
      bag(bag.size) shouldBe None
    }

    "return proper size" in {
      val bag = createBag.simple
      bag.size shouldBe 3
    }

    "remove and return element by id" in {
      val bag = createBag.simple
      bag.remove(1).value shouldBe "b"
      bag.size shouldBe 2
      bag.remove(1).value shouldBe "c"
      bag.size shouldBe 1
      bag(0).value shouldBe "a"
    }

    "return None when trying to remove not existing element by index" in {
      val bag = createBag.simple
      bag.remove(100) shouldBe None
      bag.size shouldBe 3
    }

    "remove and return last element" in {
      val bag = createBag.simple
      bag.removeLast().value shouldBe "c"
      bag.size shouldBe 2
    }

    "return None when trying to remove last element of an empty bag" in {
      val bag = createBag.empty
      bag.removeLast() shouldBe None
    }

    "remove element by reference if it exists" in {
      val bag = createBag.simple
      bag.remove("b") shouldBe true
      bag.remove("z") shouldBe false
      bag.size shouldBe 2
      bag.remove("c") shouldBe true
      bag.size shouldBe 1
      bag(0).value shouldBe "a"
    }

    "remove all elements listed in another bag and return modification status" in {
      val bag = createBag.simple
      val toRemove = MutableBag("a", "c")
      bag.removeAll(toRemove) shouldBe true
      bag.size shouldBe 1
      bag(0).value shouldBe "b"
    }

    "return false if no element was removed by removeAll()" in {
      val bag = createBag.simple
      val toRemove = MutableBag("z", "x")
      bag.removeAll(toRemove) shouldBe false
      bag.size shouldBe 3
    }

    "check if contains given element" in {
      val bag = createBag.simple
      bag.contains("b") shouldBe true
      bag.contains("z") shouldBe false
    }

    "set default bag capacity to 16" in {
      val bag = new MutableBag[String]
      bag.capacity shouldBe 16
    }

    "check if is empty" in {
      createBag.empty.isEmpty shouldBe true
      createBag.simple.isEmpty shouldBe false
    }

    "be iterable" in {
      createBag.simple.map(_.length).sum shouldBe 3
    }

    "clear the bag" in {
      val bag = createBag.simple
      bag.size shouldBe 3
      bag.clear()
      bag.size shouldBe 0
      bag(0) shouldBe None
    }
  }

}
