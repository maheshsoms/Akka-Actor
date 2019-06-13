package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.RoundRobinPool

class RoundRobinPoolActor extends Actor {

  override def receive: Receive = {
    case msg: String => println(s"I am ${self.path.name}")
    case _ =>
  }
}
object RoundRobinPoolApp extends App {
  val actorSystem=ActorSystem("round-robin")
  val roundRobin=actorSystem.actorOf(RoundRobinPool(5).props(Props[RoundRobinPoolActor]))
  for (i <- 1 to 10){
    roundRobin ! s"Hello $i"
  }
}