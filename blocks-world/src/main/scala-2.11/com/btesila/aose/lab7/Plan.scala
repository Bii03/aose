package com.btesila.aose.lab7

import akka.actor.ActorRef
import com.btesila.aose.lab7.ArmActor.Action

/**
  * Created by Bii on 11/27/2016.
  */
case class Plan(actions: List[Action], agent: ActorRef)
