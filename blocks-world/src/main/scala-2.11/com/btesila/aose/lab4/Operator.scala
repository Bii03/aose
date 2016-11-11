package com.btesila.aose.lab4

import com.btesila.aose.lab4.Predicate._

case class Operator(
    prec: List[Predicate],
    remvs: List[Predicate],
    adds: List[Predicate]) {

  def apply(state: State): State = {
    val statePreds = state.predicates

    val updatedPreds = remvs.foldLeft(statePreds)((acc, pred) =>
      acc.filter(!_.equals(pred))
    ) ::: adds

    State(updatedPreds)
  }
}

object Operator {
  def stack(block: Block, onBlock: Block): Operator = {
    val prec = List(
      Clear(onBlock),
      Hold(block)
    )
    val remvs = List(
      Clear(onBlock),
      Hold(block)
    )
    val adds = List(
      On(block, onBlock),
      EmptyArm
    )
    Operator(prec, remvs, adds)
  }

  def unstack(block: Block, fromBlock: Block): Operator = {
    val prec = List(
      On(block, fromBlock),
      Clear(block),
      EmptyArm
    )
    val remvs = List(
      On(block, fromBlock),
      EmptyArm
    )
    val adds = List(
      Hold(block),
      Clear(fromBlock)
    )
    Operator(prec, remvs, adds)
  }

  def pickUp(block: Block): Operator = {
    val prec = List(
      EmptyArm,
      Clear(block),
      OnTable(block)
    )
    val remvs = List(
      EmptyArm,
      OnTable(block)
    )
    val adds = List(
      Hold(block)
    )
    Operator(prec, remvs, adds)
  }

  def putDown(block: Block): Operator = {
    val prec = List(
      Hold(block)
    )
    val remvs = List(
      Hold(block)
    )
    val adds = List(
      OnTable(block),
      Clear(block)
    )
    Operator(prec, remvs, adds)
  }
}
