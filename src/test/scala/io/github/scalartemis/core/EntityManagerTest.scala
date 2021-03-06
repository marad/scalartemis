package io.github.scalartemis.core

import io.github.marad.scalartemis.core.EntityManager
import io.github.scalartemis.BDD
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class EntityManagerTest extends WordSpec with Matchers with BDD with MockitoSugar {

  "Entity Manager" should {
    "create new entity" in {
      Given
      val em = new EntityManager

      When
      val entity = em.acquireEntity()

      Then
      entity.id shouldBe 0
    }

    "find entities by id" in {
      Given
      val em = new EntityManager
      val e = em.acquireEntity()

      When
      val entity = em.get(e.id)

      Then
      entity.id shouldBe e.id
    }

    "release entities" in {
      Given
      val em = new EntityManager
      em.acquireEntity()

      When
      em.releaseEntity(0)

      Then
      em.entityCount shouldBe 0
    }

    "reuse released entity ids" in {
      Given
      val em = new EntityManager
      em.acquireEntity()
      em.acquireEntity()
      em.acquireEntity()
      em.releaseEntity(1)

      When
      val created = em.acquireEntity()

      Then
      created.id shouldBe 1
    }
  }

}
