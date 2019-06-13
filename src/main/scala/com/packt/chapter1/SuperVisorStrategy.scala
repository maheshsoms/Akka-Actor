package com.packt.chapter1

import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}
import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume}
import scala.concurrent.duration._

class Printer extends Actor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("Printer: Im restarting becuase of arithmetic expresion")
    }
    def receive = {
      case msg: String => println(s"printer $msg")
      case msg: Int => 1/0
    }
  }
  class IntAdder extends Actor{
    var x=0
    def receive = {
      case msg: Int => x=x+msg
        println(s"Intadder: sum is $x")
      case msg: String => throw new IllegalArgumentException
    }
    override def postStop(): Unit = {
      println("IntAdder: Iam getting stopped because i got a string message")
    }
  }


class SuperVisorStrategy extends Actor {

    val superVisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Restart
    case _: NullPointerException => Resume
    case _: IllegalArgumentException => Resume
    case _: Exception => Escalate
  }
  val printer = context.actorOf(Props[Printer])
  val intAdder = context.actorOf(Props[IntAdder])

  def receive = {
    case "Start" => printer ! "Hello Printer"
      printer ! 10
      intAdder ! 10
      intAdder ! 10
      intAdder ! "Hello int adder"
  }
}

object SuperVisorStrategyApp extends App{
  val actorSystem =ActorSystem("interactive-messging")
  actorSystem.actorOf(Props[SuperVisorStrategy]) ! "Start"
}


