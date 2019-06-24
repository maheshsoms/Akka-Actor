package com.packt.chapter5

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._

class RandonIntAdder extends Actor {
  val r = scala.util.Random

  def receive = {
    case "tick" => {
      val randomInta = r.nextInt(10)
      val randomIntb = r.nextInt(10)
      println(s"sum of $randomInta and $randomIntb is ${randomInta + randomIntb}")
    }

  }
}

object ScheduleActor extends App {
  val system = ActorSystem("schedule-actor")

  import system.dispatcher

  val actor = system.actorOf(Props[RandonIntAdder])
  system.scheduler.scheduleOnce(10 seconds, actor, "tick")
  system.scheduler.schedule(11 seconds, 2 seconds, actor, "tick")
}
