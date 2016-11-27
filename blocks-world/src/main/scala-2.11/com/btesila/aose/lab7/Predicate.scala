package com.btesila.aose.lab7

trait Predicate { self: Arm =>

  def on(A: Block, B: Block): Boolean = {
    for ( i <- 0 to currentStack.length - 2) {
      if (currentStack(i).equals(A) && currentStack(i + 1).equals(B)) {
        return true
      }
    }
    false
  }

  def clear(A: Block): Boolean =
    currentStack(0).equals(A)

  def onTable(A: Block): Boolean =
    currentStack(currentStack.length - 1).equals(A)

  def hold(A: Block): Boolean =
    self.currentBlock.isDefined && self.currentBlock.get.equals(A)

  def emptyArm(): Boolean =
    self.currentBlock.isEmpty
}
