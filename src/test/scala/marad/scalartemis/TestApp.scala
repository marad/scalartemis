package marad.scalartemis

import marad.scalartemis.core._

object TestApp {

  class C1 extends Component
  class C2 extends Component

  class TestSystem extends EntitySystem(Aspect.forAll(classOf[C1], classOf[C2])) {
    override def process(entities: Iterable[Entity]): Unit = {
      entities.foreach { entity =>
        println(s"Processing entity ${entity.id}")
        val c = component[C1](entity)
      }
    }
  }

  def main(args: Array[String]) = {
    val world = new World
    world.registerSystem(new TestSystem)
    world.createEntity(new C1, new C2)
    world.createEntity(new C1, new C2)
    world.createEntity(new C1)
    world.createEntity(new C2)

    world.update(1/60f)
  }
}
