package io.github.marad.scalartemis.systems

import io.github.marad.scalartemis.{EntitySystem, Aspect}

/**
 * Processes no entities
 */
abstract class VoidEntitySystem extends EntitySystem(Aspect.none)
