package io.github.marad.scalartemis.examples

import io.github.marad.scalartemis._
import io.github.marad.scalartemis.systems.SequentialProcessingSystem

object Example1 {
  // Declaring used components
  class Player extends Component
  case class Position(var x: Int, var y: Int) extends Component
  case class RenderInfo(symbol: Char) extends Component

  // System for controlling entity positions
  class ControlSystem(val world: World)
    extends SequentialProcessingSystem(Aspect.forAll[Position, Player])
    with ComponentMapping {

    override def process(entity: Entity, delta: Float): Unit = {
      val position = component[Position](entity).get
      position.x += 1
      position.y += 1
    }
  }

  // System for rendering entities on screen
  class RenderSystem(val world: World)
    extends SequentialProcessingSystem(Aspect.forAll[Position, RenderInfo])
    with ComponentMapping {

    override def process(entity:Entity, delta: Float): Unit = {
//      terminal.clear()
      (component[Position](entity), component[RenderInfo](entity)) match {
        case (Some(position), Some(info)) =>
//          terminal.setCursorPosition(position.x, position.y)
//          terminal.putCharacter(info.symbol)
        case _ =>
      }
    }
  }

  def main(args: Array[String]): Unit = {
    // Create world and register created systems
    val world = new World
    world.registerSystem(new ControlSystem(world))
    world.registerSystem(new RenderSystem(world))

    // Create entities with starting components
    world.createEntity(Position(0, 0), RenderInfo('@'), new Player)
    world.createEntity(Position(5, 5), RenderInfo('$'))

    // Run world update loop
    for(i <- 0 to 5) {
      world.update(1f)
    }
  }
}
