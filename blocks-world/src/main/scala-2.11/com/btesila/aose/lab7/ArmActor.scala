package com.btesila.aose.lab7

import akka.actor.{Actor, ActorLogging, Props}
import com.btesila.aose.lab7.ArmActor.Actions.PickUp
import com.btesila.aose.lab7.ArmActor.{NoToken, Token}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Bii on 11/27/2016.
  */
class ArmActor(inState: ListBuffer[mutable.Stack[Block]]) extends Arm(inState) with Actor {
  import ArmActor.Actions._
 def receive = {
   case NoToken => context.become(waiting)
   case Stack(a, b) => stack(a, b)
   case Unstack(a, b) => unstack(a, b)
   case PickUp(a) => pickUp(a)
   case PutDown(a) => a
 }

  def waiting: Receive = {
    case Token(action) => {
      context.become(receive)
      self ! action
    }
  }
}

object ArmActor {
  sealed trait Action
  object Actions {
    case class Stack(A: Block, B: Block) extends Action
    case class Unstack(A: Block, B: Block) extends Action
    case class PickUp(A: Block) extends Action
    case class PutDown(A: Block) extends Action
  }

  case class Token(action: Action)
  case object NoToken

  def props(initial: ListBuffer[mutable.Stack[Block]]): Props = Props(classOf[ArmActor], initial)
}


