package com.btesila.aose.lab7

import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Bii on 11/27/2016.
  */
object World {

  object Blocks {
    val A: Block = Block("A")
    val B: Block = Block("B")
    val C: Block = Block("C")
    val D: Block = Block("D")
    val E: Block = Block("E")
    val F: Block = Block("F")
  }
  private


  def world1(): ListBuffer[mutable.Stack[Block]] = {
    import Blocks._
    val world = new ListBuffer[mutable.Stack[Block]]
    // initial configuration
    val stackAB = new mutable.Stack[Block]
    stackAB.push(A)
    stackAB.push(B)
    world += stackAB

    val stackC = new mutable.Stack[Block]
    stackC.push(C)
    world += stackC

    val stackD = new mutable.Stack[Block]
    stackD.push(D)
    world += stackD

    val stackEF = new mutable.Stack[Block]
    stackEF.push(E)
    stackEF.push(F)
    world += stackEF
  }

  def world2(): ListBuffer[mutable.Stack[Block]] = {
    import Blocks._
    val world = new ListBuffer[mutable.Stack[Block]]
    // initial configuration
    val stackABC = new mutable.Stack[Block]
    stackABC.push(C)
    stackABC.push(B)
    stackABC.push(A)
    world += stackABC
  }

  def getWorld() = world2
}
