package marad.scalartemis.core

import marad.scalartemis.BDD
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class EntityManagerTest extends WordSpec with Matchers with BDD with MockitoSugar {

  val world = mock[World]

  "Entity Manager" should {
    "create new entity" in {
      Given
      val em = new EntityManager(world)

      When
      val entity = em.acquireEntity()

      Then
      entity.id shouldBe 0
    }

    "find entities by id" in {
      Given
      val em = new EntityManager(world)
      em.acquireEntity()

      When
      val entity = em.get(0)

      Then
      entity shouldNot be (null)
    }

    "release entities" in {
      Given
      val em = new EntityManager(world)
      val entity = em.acquireEntity()

      When
      em.releaseEntity(entity)

      Then
      em.entityCount shouldBe 0
    }

    "reuse released entity ids" in {
      Given
      val em = new EntityManager(world)
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
