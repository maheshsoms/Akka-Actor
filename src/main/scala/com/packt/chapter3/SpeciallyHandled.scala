package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Kill, Props}
import akka.routing.{Broadcast, RandomPool}

case object Handle

class SpeciallyHandled extends Actor{
  override def receive ={
    case Handle => println(s"${self.path.name} says hello")
  }

}

object SpeciallyHandled extends App {
  val actorSystem=ActorSystem("Specially-Handled")
  val speciallyHandled=actorSystem.actorOf(RandomPool(5).props(Props[SpeciallyHandled]))

  speciallyHandled ! Broadcast(Handle)
  speciallyHandled ! Broadcast(Kill)
  speciallyHandled ! Handle
}
