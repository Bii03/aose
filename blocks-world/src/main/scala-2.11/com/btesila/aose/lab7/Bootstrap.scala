package com.btesila.aose.lab7

import akka.actor.{ActorRef, ActorSystem}
import com.btesila.aose.lab7.ArmActor.Actions.{PickUp, PutDown, Unstack}
import com.btesila.aose.lab7.ArmActor.{NoToken, Token}

/**
  * Created by Bii on 11/27/2016.
  */
object Bootstrap extends App {
  import World.Blocks._

  val world = World.getWorld()
  println("Initial state:")
  dispayWorld(world)

  val arm1 = new Arm(world)
  val arm2 = new Arm(world)
  arm1.unstack(B, A)
  arm1.stack(B, D)
  arm1.pickUp(C)
  arm1.stack(C, A)
  arm1.unstack(F, E)
  arm1.stack(F, C)
  arm1.pickUp(E)
  arm1.stack(E, B)

  println("Final state:")
  dispayWorld(arm1.world)

  println("Two agents, one coordinator")

  val as = ActorSystem("blocks-world")

  val agent1 = as.actorOf(ArmActor.props(world), "agent-one")
  val actions1: List[ArmActor.Action] = List(Unstack(A, B), PutDown(A), Unstack(B, C), PutDown(B), PickUp(A), ArmActor.Actions.Stack(A, B))
  var plan1 = Plan(actions1, agent1)

  val agent2 = as.actorOf(ArmActor.props(world), "agent-two")
  val actions2: List[ArmActor.Action] = List(Unstack(A, B), PutDown(A), Unstack(B, C), PutDown(B))
  var plan2 = Plan(actions2, agent2)

  var waitingAgent: ActorRef = null
  (plan1.actions, plan2.actions) match {
    case (action1 :: tail1, action2 :: tail2 ) if action1 == action2 => {
      plan1 = plan1.copy(actions = tail1)
      plan1.agent ! action1
      waitingAgent = agent2
      plan2.agent ! NoToken
    }
    case (action1 :: tail1, action2 :: tail2 ) if waitingAgent == null => {
      plan1.agent ! action1
      plan2.agent ! action2
      plan1 = plan1.copy(actions = tail1)
      plan2 = plan2.copy(actions = tail2)
    }

    case (action1 :: tail1, action2 :: tail2) if waitingAgent != null && action1 != action2 => {
      if (plan1.agent == waitingAgent) {
        waitingAgent ! Token(action1)
        plan2.agent ! action2
      }
      if (plan2.agent == waitingAgent) {
        plan1.agent ! action1
        waitingAgent ! Token(action2)
      }
      plan1 = plan1.copy(actions = tail1)
      plan2 = plan2.copy(actions = tail2)
      waitingAgent = null
    }
    case (action1 :: tail1, action2 :: tail2) if waitingAgent != null && action1 == action2 => {

    }
    case (Nil, action2 :: tail2) => {
      if (waitingAgent == agent2)  agent2 ! Token(action2)
      tail2.foreach(action => plan2.agent ! action)
    }
    case (action1 :: tail1, Nil) => {
      if (waitingAgent == agent2)  agent2 ! Token(action1)
      tail1.foreach(action => plan2.agent ! action)
    }
  }
}
