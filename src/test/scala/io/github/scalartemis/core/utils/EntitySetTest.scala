package io.github.scalartemis.core.utils

import io.github.marad.scalartemis.Entity
import io.github.marad.scalartemis.core.utils.EntitySet
import io.github.scalartemis.BDD
import org.scalatest.{Matchers, WordSpec}

class EntitySetTest extends WordSpec with Matchers with BDD {
  "EntitySet" should {
    "not duplicate entities" in {
      Given
      val set = new EntitySet
      val e1 = new Entity(1)
      val e2 = new Entity(2)

      When
      set.add(e1)
      set.add(e1)
      set.add(e2)
      set.add(e2)

      Then
      set.entities should have size 2
      set.entities should contain (e1)
      set.entities should contain (e2)
    }
  }
}
