package com.packt.chapter5

import akka.actor.ActorSystem

import scala.concurrent.duration._

object ScedhuleOperation extends App{
  val system = ActorSystem("Schedule-Actor")
  import system.dispatcher
  system.scheduler.scheduleOnce(10 seconds)
  {
    println(s"sum of (1+2) is ${1+2}")
  }
  system.scheduler.schedule(11 seconds,2 seconds){
    println(s"hello, sorry for disturbing you every 2 seconds")
  }
}