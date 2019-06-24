package com.packt.chapter1

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.ControlMessage

case object MyControlMessage extends ControlMessage

class Logger extends Actor {
  def receive = {
    case MyControlMessage => println("I have to process control Message")
    case x => println(x.toString)
  }
}

object ControlAwareMailbox extends App {
  val actorSystem = ActorSystem("Control-Message")
  val priorityMailBox = actorSystem.actorOf(Props[Logger].withDispatcher("control-aware-dispatcher"))

  priorityMailBox ! "some"
  priorityMailBox ! "hello"
  priorityMailBox ! MyControlMessage
}
