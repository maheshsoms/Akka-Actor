package com.packt.chapter1

import akka.actor.Actor

class SummingActor extends Actor {

  var sum = 1

  override def receive: Receive = {
    case x: Int => sum = sum + x
      println(s"my state as sum is $sum")
    case _ => println("i dont know what you are talking")
  }

}
