package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.BalancingPool

class BalancingPoolActor extends Actor {

  override def receive: Receive = {
    case msg: String => println(s"Im ${self.path.name}")
    case _ => println(s"i dont understand the message")
  }
}

object balancingpool extends App {
  val actorSytem = ActorSystem("Balancing-pool")
  val router = actorSytem.actorOf(BalancingPool(5).props(Props[BalancingPoolActor]))

  for (i <- 1 to 5) {
    router ! s"hello $i"
  }
}