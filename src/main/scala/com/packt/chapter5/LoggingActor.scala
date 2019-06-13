package com.packt.chapter5

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

class LoggingActor extends Actor with ActorLogging {
  def receive ={
    case(a: Int,b: Int) => {
      log.info(s"sum of $a and $b is ${a+b}")
    }
    case msg => log.warning(s"i dont handle string messages: $msg")
  }
}

object Logging extends App {
  val system=ActorSystem("Loggin-Actor")
  val actor=system.actorOf(Props[LoggingActor],"sum-actor")

  actor ! "some message"
  actor ! (10,20)

  system.terminate()
}
