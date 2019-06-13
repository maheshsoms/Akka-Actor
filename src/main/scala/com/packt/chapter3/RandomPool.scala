package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.RandomPool


class RandomPoolActor extends Actor {
  override def receive ={
    case msg: String => println(s"Im ${self.path.name}")
    case _ => println("i dont understand these type of messages")
  }
}

object RandomPoolApp extends App {
  val actorSystem=ActorSystem("Random-ACtor")
  val router=actorSystem.actorOf(RandomPool(5).props(Props[RandomPoolActor]))

  for(i <- 1 to 5){
    router ! s"Hello $i"
  }
}