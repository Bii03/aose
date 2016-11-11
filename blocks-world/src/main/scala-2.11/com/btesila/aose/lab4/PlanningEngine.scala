package com.btesila.aose.lab4

import com.btesila.aose.lab4.Predicate._

import scala.util.{Failure, Success, Try}

class PlanningEngine(initialState: State, goal: State) {
  var states = List(initialState)// List of states transited
  var operators = List.empty[Operator] // List of operators applied
  var stack: List[Any] = Preconditions(goal.predicates) :: goal.predicates // Stack of the algorithm
  var solve_stack = List.empty[Predicate] // Stack of preconditions being solved
  var prev_states = List.empty[State] // States of the previous algorithm

  var init_state = initialState // Initial state
  var goal_state = goal // Goal state
  var curr_state = initialState // Current state
  var is_valid = false // Has the algorithm found a valid solution?

  def executePlan(): Try[String] = {
    println(s"Current stack $stack")
    println(s"Current state $curr_state")
    println(stack(0))
    println(stack.tail)
//    while (!stack.isEmpty) {
//      val currentAction = stack(0)
//      println(s"Stack before drop ${stack.size}")
//      stack = stack.tail
//      println(s"Stack after drop ${stack.size}")
//
//
//      currentAction match {
//        case o: Operator =>
//          // Update the current state by applying the operator
//          curr_state = o.apply(curr_state)
//          // Fail if already visited this state
//          if (isStateVisited(curr_state)) Failure(new RuntimeException("Already"))
//          // Add new state & operator to plan
//          states = states :+ curr_state
//          operators = operators :+ o
//
//        case prec@Preconditions(preds) =>
//          // Checking the list of conditions
//          println(s"Found preconditions $preds")
//          val unmetPreds = preds.toSet.--(curr_state.predicates).toList
//          println(s"Unmet $unmetPreds")
//          if (!unmetPreds.isEmpty) stack = (stack :+ prec) ::: unmetPreds
//          println(s"Stack $stack")
////
////        case p: Predicate =>
////          println(s"P $p")
////          stack = stack :+ p
//
//        case p: Predicate if !curr_state.predicates.contains(p) =>
//          println(s"Actual state: $curr_state")
//          println(s"Checking condition: $p")
//          // Add the predicate to be solved
//          solve_stack = solve_stack :+ p
//          println(s"TO solve $solve_stack")
//          var found = false
//          val potentialOperators = retrievePotentialOperators(p)
//          println(s"Potential operators $potentialOperators")
//
//          potentialOperators.takeWhile(_ => !found).foreach { op =>
//            println(s"Taking while for $op")
//            val sts = prev_states ::: states
//            val alg = new PlanningEngine(curr_state, State(List.empty))
//            println(s"New alg $alg")
//            alg.run(curr_state, op, solve_stack, sts) match {
//              case Success(_) =>
//                println("It's a success")
//                val tstates = alg.states
//                val toperators = alg.operators
//                states = states ::: tstates
//                operators = operators ::: toperators
//                println(s"Current plan: $operators")
//                println(s"Operators added to the plan: $toperators")
//                curr_state = states.last
//                found = true
//
//              case Failure(_) =>
//                println(s"Operator discarded: $op")
//            }
//            println(s"Solving stack $solve_stack")
//            solve_stack = solve_stack.drop(solve_stack.indexOf(p))
//            println(s"Solve stack $solve_stack")
//            if (!found) Failure(new RuntimeException("No solution"))
//          }
//        case p =>
//          println(s"Unknown $p")
//          println(s"Current $curr_state")
//      }
//    }
    Success("lala")
  }

  /**
    * Solve a partial problem.
    *
    * @param initialState
    * @param op
    * @param predToSolve
    * @param prevStates
    * @return
    */
  def run(initialState: State, op: Operator, predToSolve: List[Predicate], prevStates: List[State]): Try[String] = {
    curr_state = initialState
    states = states :+ curr_state

    val preconditions = Preconditions(op.prec)
    stack = (stack :+ op :+ preconditions) ::: preconditions.predicates

    solve_stack = predToSolve
    prev_states = prevStates

    executePlan()
  }

  private def isStateVisited(state: State): Boolean =
    states.contains(state) && prev_states.contains(state)

  private def retrievePotentialOperators(p: Predicate): List[Operator] = {
    var potentialOps = List.empty[Operator]
    p match {
      case On(block, onBlock) =>
        potentialOps = List(Operator.stack(block, onBlock))
      case OnTable(block) =>
        potentialOps = List(Operator.putDown(block))
      case Clear(block) =>
        potentialOps = List(Operator.unstack(null, block))
      case Hold(block) =>
        if (curr_state.predicates.contains(OnTable(block))) {
          potentialOps = potentialOps :+ Operator.pickUp(block) :+ Operator.unstack(block, null)
        } else {
          potentialOps = potentialOps :+ Operator.unstack(block, null) :+ Operator.pickUp(block)
        }
      case EmptyArm =>
        curr_state.predicates.collectFirst {
          case Hold(block) =>
            goal_state.predicates.collectFirst {
              case OnTable(block) =>
                potentialOps = potentialOps :+ Operator.putDown(block)
            }
            if (potentialOps.isEmpty) potentialOps = potentialOps :+ Operator.stack(block, null)
        }
    }
    potentialOps
  }
}
