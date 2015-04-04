package marad.scalartemis

object TestApp {

  class C1 extends Component {
    val msg = "Hello"
  }
  class C2 extends Component

  class TestSystem(val world: World)
    extends EntitySystem(Aspect.forAll(classOf[C1]))
    with ComponentMapping
  {
    override def process(delta: Float): Unit = {
      entities.foreach { entity =>
        val c: Option[C1] = component[C1](entity)
        println(s"Processing entity ${entity.id}. Message: ${c.get.msg}")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val world = new World
    world.registerSystem(new TestSystem(world))
    world.createEntity(new C1, new C2)
    world.createEntity(new C1, new C2)
    world.createEntity(new C2)
    world.createEntity(new C1)

    world.update(1/60f)
  }
}
