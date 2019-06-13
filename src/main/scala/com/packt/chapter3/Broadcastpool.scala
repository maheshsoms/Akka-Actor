package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.BroadcastPool
class BroadCastPoolActor extends Actor {
  override def receive: Receive = {
    case msg: String => println(s" im okkalaoli  ${self.path.name}")
    case _ => println(s" im okkalaoli ${self.path.name}")
  }
}

object Broadcastpool extends App {
  val actorSystem=ActorSystem("BroadCastPool")
  val router=actorSystem.actorOf(BroadcastPool(5).props(Props[BroadCastPoolActor]))
   for ( i <- 1 to 10){
     router ! s"pongada okkala oligala ${i}"
   }
}