package com.btesila.aose.lab4

import com.btesila.aose.lab4.Predicate.{EmptyArm, On, OnTable}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

case class State(predicates: List[Predicate])


object State {
  import com.btesila.aose.lab4.State.Blocks._

  private val config = ConfigFactory.load("application.conf")

  val initialState = loadState("initial")
  val finalState = loadState("final")

  private def loadState(ns: String): State = {
    val stateNs = config.getConfig("state").getConfig(ns)
    val onTable: List[Predicate] =
      stateNs.getStringList("on-table")
        .toList
        .map(name => OnTable(findBlock(name)))
    val onBlock: List[Predicate] =
      stateNs.getObject("on")
        .toMap
        .map { case (key, value) =>
            On(findBlock(key), findBlock(value.unwrapped().toString))
        }
        .toList
    val predicates = onTable ::: onBlock
   State(EmptyArm :: predicates)
  }

  object Blocks {
    private val blocksConfig = config.getStringList("blocks").toList
    val blocks = blocksConfig.map(Block(_))

   def findBlock(name: String): Block =
     blocks.find(_.name == name).get
  }
}