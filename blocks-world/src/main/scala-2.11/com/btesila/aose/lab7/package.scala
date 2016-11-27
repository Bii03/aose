package com.btesila.aose

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Bii on 11/27/2016.
  */
package object lab7 {
  def dispayWorld(world: ListBuffer[mutable.Stack[Block]]): Unit = {
    world.foreach { stack =>
      stack.foreach(block => print(s"$block, "))
      println()
    }
  }
}
