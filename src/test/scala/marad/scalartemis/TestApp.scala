package marad.scalartemis

import marad.scalartemis.core._
import marad.scalartemis.core.entity.Entity

object TestApp {

  class C1 extends Component
  class C2 extends Component

  class TestSystem(val world: World)
    extends EntitySystem(Aspect.onlyFor(classOf[C1]))
    with ComponentMapping
  {
    override def process(entity: Entity, delta: Float): Unit = {
      println(s"Processing entity ${entity.id}")
      val c: Option[C1] = component[C1](entity)
    }
  }

  def main(args: Array[String]): Unit = {
    val world = new World
    world.registerSystem(new TestSystem(world))
    world.createEntity(new C1, new C2)
    world.createEntity(new C1, new C2)
    world.createEntity(new C1)
    world.createEntity(new C2)

    world.update(1/60f)
  }
}
