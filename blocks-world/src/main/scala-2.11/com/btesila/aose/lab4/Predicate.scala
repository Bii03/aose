package com.btesila.aose.lab4

sealed trait Predicate

object Predicate {
  case class On(block: Block, onBlock: Block) extends Predicate
  case class OnTable(block: Block) extends Predicate
  case class Clear(block: Block) extends Predicate
  case class Hold(block: Block) extends Predicate
  case object EmptyArm extends Predicate

  case class Preconditions(predicates: List[Predicate])
}
