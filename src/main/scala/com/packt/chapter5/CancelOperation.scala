package com.packt.chapter5

import akka.actor.{Actor, ActorSystem, Cancellable, Props}

import scala.concurrent.duration._

class Cancellation extends Actor {
  var i=10
  def receive = {
    case "tick" => {
      println(s"do you know i do the task again and again")
      i-=1
      if(1==0) Scheduler.cancellable.cancel()
    }
  }
}

object Scheduler extends App {
  val system=ActorSystem("cancellable")
  import system.dispatcher

  val actor=system.actorOf(Props[Cancellation])

  val cancellable: Cancellable =system.scheduler.schedule(0 seconds,2 seconds,actor,"tick")
}