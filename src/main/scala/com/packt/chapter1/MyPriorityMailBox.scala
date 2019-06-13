package com.packt.chapter1

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

class MyPriorityActor extends Actor {

  override def receive: PartialFunction[Any,Unit] = {
    case x: Int => println(x)
    case x: String => println(x)
    case x: Long => println(x)
    case x => println(x)
  }
}


class MyPriorityActorMailBox(settings: ActorSystem.Settings,config: Config) extends UnboundedPriorityMailbox (
  PriorityGenerator{
    case x: Int => 1
    case x: String => 0
    case x: Long => 2
    case _ => 3
  }

)


object MyPriorityMailBox extends App{
  val actorSystem =ActorSystem("Priority_MailBox")
  val priorityActor=actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"))

  priorityActor ! 6.0
  priorityActor ! 2.33
  priorityActor ! 5
  priorityActor ! "strings gets processed first like me"

}