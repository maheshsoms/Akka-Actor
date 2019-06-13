package com.packt.chapter4

import akka.agent.Agent

import scala.concurrent.ExecutionContext.Implicits.global

object AgentComposition extends App {
  val agent1=Agent("Hello ")
  val agent2=Agent("World")

  val finalAgent = for {
    x <- agent1
    y <- agent2
  } yield x + y
  println(finalAgent.get)
}