package marad.scalartemis.systems

import marad.scalartemis.{Aspect, EntitySystem}

/**
 * Processes no entities
 */
abstract class VoidEntitySystem extends EntitySystem(Aspect.none)
