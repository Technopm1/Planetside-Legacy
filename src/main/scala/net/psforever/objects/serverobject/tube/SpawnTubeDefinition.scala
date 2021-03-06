// Copyright (c) 2017 PSForever
package net.psforever.objects.serverobject.tube

import akka.actor.ActorContext
import net.psforever.objects.{Default, SpawnPointDefinition}
import net.psforever.objects.definition.converter.SpawnTubeConverter
import net.psforever.objects.serverobject.PlanetSideServerObject
import net.psforever.objects.serverobject.structures.{Amenity, AmenityDefinition}

/**
  * The definition for any spawn point in the game world.
  */
class SpawnTubeDefinition(object_id: Int) extends AmenityDefinition(object_id) with SpawnPointDefinition {
  Packet = new SpawnTubeConverter
  registerAs = "terminals"
}

object SpawnTubeDefinition {

  /**
    * Assemble some logic for a provided object.
    * @param obj an `Amenity` object;
    *            anticipating a `Terminal` object using this same definition
    * @param context hook to the local `Actor` system
    */
  def Setup(obj: Amenity, context: ActorContext): Unit = {
    import akka.actor.Props
    if (obj.Actor == Default.Actor) {
      obj.Actor = context.actorOf(Props(classOf[SpawnTubeControl], obj), PlanetSideServerObject.UniqueActorName(obj))
    }
  }
}
