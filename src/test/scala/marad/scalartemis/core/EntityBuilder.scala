package marad.scalartemis.core

import scala.collection.mutable

class EntityBuilder {
  private var world: World = null
  private var id: Int = 0
  private var components: mutable.MutableList[Component] = new mutable.MutableList[Component]

  def withWorld(world: World) = {
    this.world = world
    this
  }
  def withId(id: Int) = {
    this.id = id
    this
  }
  def withComponents(components: Component*) = {
    this.components ++= components
    this
  }

  def build() = {
    val entity = new Entity(world, id)
    components.foreach(entity.addComponent)
    entity
  }
}
