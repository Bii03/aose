package com.btesila.aose.lab7

import akka.actor.{Actor, Props}
import akka.event.LoggingReceive
import com.btesila.aose.lab7.ArmActor.{DisplayWorld, NoToken, Token, WakeUp}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class ArmActor(inState: ListBuffer[mutable.Stack[Block]]) extends Arm(inState) with Actor {
  import ArmActor.Actions._

  println(s"Starting actor ${self.path}")
 def receive = LoggingReceive {
   case NoToken => context.become(waiting)
   case Stack(a, b) => stack(a, b)
   case Unstack(a, b) => unstack(a, b)
   case PickUp(a) => pickUp(a)
   case PutDown(a) => putDown(a)
   case DisplayWorld => displayState(self.path.toString)
 }

  def waiting: Receive = LoggingReceive {
    case Token(action) => {
      context.become(receive)
      self ! action
    }
    case WakeUp =>
      context.become(receive)
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
  case object DisplayWorld
  case object WakeUp

  def props(initial: ListBuffer[mutable.Stack[Block]]): Props = Props(classOf[ArmActor], initial)
}


