package com.btesila.aose.lab7

import akka.actor.{ActorRef, ActorSystem}
import com.btesila.aose.lab7.ArmActor.Actions.{PickUp, PutDown, Unstack}
import com.btesila.aose.lab7.ArmActor.{DisplayWorld, NoToken, Token, WakeUp}

object Bootstrap extends App {
  import World.Blocks._

  val world = World.getWorld()
  println("Initial state:")
  dispayWorld(world)

//  val arm1 = new Arm(world)
//  val arm2 = new Arm(world)
//  arm1.unstack(B, A)
//  arm1.stack(B, D)
//  arm1.pickUp(C)
//  arm1.stack(C, A)                             ª
//  arm1.unstack(F, E)
//  arm1.stack(F, C)
//  arm1.pickUp(E)
//  arm1.stack(E, B)

//  println("Final state:")
//  dispayWorld(arm1.world)

  println("Two agents, one coordinator")

  val as = ActorSystem("blocks-world")

  val agent1 = as.actorOf(ArmActor.props(world), "agent-one")
  val actions1: List[ArmActor.Action] = List(Unstack(A, B), PutDown(A), Unstack(B, C), PutDown(B), PickUp(A), ArmActor.Actions.Stack(A, B))
  var plan1 = Plan(actions1, agent1)

  val agent2 = as.actorOf(ArmActor.props(world), "agent-two")
  val actions2: List[ArmActor.Action] = List(Unstack(A, B), PutDown(A), Unstack(B, C), PutDown(B))
  var plan2 = Plan(actions2, agent2)

  coordinatePlanning(plan1, plan2)

  Thread.sleep(3000)
  agent1 ! DisplayWorld
  agent2 ! DisplayWorld

  var previous: ActorRef = null
  var round = 0
  def coordinatePlanning(plan1: Plan, plan2: Plan, waitingAgent: ActorRef = null): Unit = {
//    println(s"Round $round agent1 ${plan1.actions} \n agent2 ${plan2.actions} \n waiting $waitingAgent")
    round += 1
    (plan1.actions, plan2.actions) match {
      case (action1 :: tail1, action2 :: tail2) if waitingAgent != null && action1 == action2 => {
        waitingAgent match {
          case plan2.agent => {
            previous = plan2.agent
            plan1.agent ! action1
            plan2.agent ! Token(tail2(0))
            val act = if(tail2(0) == tail1(0)) tail1.drop(1) else tail1
            println(previous)
            coordinatePlanning(plan1 = plan1.copy(actions = act), plan2 = plan2.copy(actions = tail2.drop(1)))
          }
          case plan1.agent => {
            plan2.agent ! action1
            plan1.agent ! Token(tail1(0))
            val act = if (tail1(0) == tail2(0)) tail2.drop(1) else tail2
            coordinatePlanning(plan1 = plan1.copy(actions = tail1.drop(1)), plan2 = plan2.copy(actions = act))
          }
        }
      }
      case (action1 :: tail1, action2 :: tail2 ) if action1 == action2 => {
        if (previous == agent2) {
          plan2.agent ! action2
          plan1.agent ! tail1(0)
          coordinatePlanning(plan1 = plan1.copy(actions = tail1.drop(1)), plan2 = plan2.copy(actions = tail2))
          previous = null
        }
        else {
          plan1.agent ! action1
          plan2.agent ! NoToken
          coordinatePlanning(plan1 = plan1.copy(actions = tail1), plan2 = plan2.copy(actions = tail2), waitingAgent = plan2.agent)
          previous = plan2.agent
        }
      }
      case (action1 :: tail1, action2 :: tail2 ) if waitingAgent == null => {
        plan1.agent ! action1
        plan2.agent ! action2
        coordinatePlanning(plan1 = plan1.copy(actions = tail1), plan2 = plan2.copy(actions = tail2))
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
        coordinatePlanning(plan1 = plan1.copy(actions = tail1), plan2 = plan2.copy(actions = tail2))
      }

      case (Nil, action2 :: tail2) => {
        println(s"No actions left for agent1 $action2")
        if (waitingAgent == agent2)  agent2 ! Token(action2)
        if (waitingAgent == agent1)  agent1 ! WakeUp
        tail2.foreach(action => plan2.agent ! action)
      }
      case (action1 :: tail1, Nil) => {
        println(s"Remaining actions for agent 1 $action1")
        if (waitingAgent == agent1)  agent1 ! Token(action1)
        else agent1 ! action1
        if (waitingAgent == agent2) agent2 ! WakeUp
        tail1.foreach(action => plan1.agent ! action)
      }
    }
  }
}
