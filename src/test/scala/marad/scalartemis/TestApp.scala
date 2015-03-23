package marad.scalartemis

import marad.scalartemis.core.{Component, World}

object TestApp {
  def main(args: Array[String]) = {
    val world = new World
    //world.registerSystem()
    world.initialize()

    val entity = world.createEntity()
    entity.addComponent(new Component)
    entity.activate()

    world.update(1/60f)
  }
}
