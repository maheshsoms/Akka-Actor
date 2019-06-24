package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.SmallestMailboxPool

class SmallestmailboxActor extends Actor {
  override def receive = {
    case msg: String => println(s"im am ${self.path.name}")
    case _ => println("i dont understand the message")
  }
}

object Smallestmailbox extends App {
  val actorSystem = ActorSystem("Someactor")
  val smallestmailboxActor = actorSystem.actorOf(SmallestMailboxPool(5).props(Props[SmallestmailboxActor]))

  for (i <- 1 to 10) {
    smallestmailboxActor ! s"hello $i"
  }


}
