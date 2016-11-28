package com.btesila.aose.lab7

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Bii on 11/27/2016.
  */
class Arm(inState: ListBuffer[mutable.Stack[Block]]) extends Predicate {
  val world = inState
  var currentStack = new mutable.Stack[Block]
  var currentBlock: Option[Block] = None

  def stack(A: Block, B: Block): Unit = {
    world.foreach { stack =>
      if (stack.contains(B)) currentStack = stack
    }
    currentStack = world.find(_.contains(B)).get
    if ( hold(A) && clear(B)) {
      currentStack.push(A)
      currentBlock = None
    }
  }

  def unstack(A: Block, B: Block): Unit = {
    currentStack = world.find(stack => stack.contains(A) && stack.contains(B)).get
    if (emptyArm() && clear(A) && on(A, B)) {
      currentStack.pop()
      currentBlock = Some(A)
    }
  }

  def pickUp(A: Block): Unit = {
    currentStack = world.find(_.contains(A)).get
    if (emptyArm() && clear(A) && onTable(A)) {
      currentStack.pop()
      world -= currentStack
      currentBlock = Some(A)
    }
  }

  def putDown(A: Block): Unit = {
    if (hold(A)) {
      val newStack = new mutable.Stack[Block]
      newStack.push(A)
      world += newStack
      currentBlock = None
    }
  }

  def displayState(agent: String): Unit = {
    println(s"Final configuration for $agent: \n $world")
  }
}
