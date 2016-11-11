package com.btesila.aose.lab4

import scala.util.{Failure, Success}

object Bootstrap extends App {
  println(State.initialState)
  println(State.finalState)

  val planning = new PlanningEngine(State.initialState, State.finalState)
  planning.executePlan() match {
    case Success(_) =>
      println("Result of the planning")
      planning.operators.foreach(println)
    case Failure(_) => println("No solution")
  }
}
