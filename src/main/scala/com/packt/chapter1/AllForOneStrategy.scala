
package com.packt.chapter1

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume}
import akka.actor.{Actor, ActorRef, ActorSystem, AllForOneStrategy, Props}
import scala.concurrent.duration._

case class Add(a: Int,b: Int)
case class Sub(a: Int,b: Int)
case class Div(a: Int,b: Int)


class Calculator(printer: ActorRef)
extends Actor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("Calculator : I am restarting because of ArithmeticException")
  }

   def receive ={
    case Add(a,b)=> printer ! s"sum is ${a+b}"
    case Sub(a,b) => printer ! s"diff is ${a-b}"
    case Div(a,b) => printer ! s"div ${a/b}"
  }
}

class ResultPrinter extends Actor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("printer: Im am restarting as well")
    }
  def receive = {
    case msg => println(msg)
  }
  }

class AllForOneStrategySupervisor extends Actor {
  override val supervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 seconds)
    {
      case _: ArithmeticException => Restart
      case _: NullPointerException => Resume
      case _: IllegalArgumentException => Restart
      case _: Exception => Escalate
    }
  val printer = context.actorOf(Props[ResultPrinter])
  val calculator =
    context.actorOf(Props(classOf[Calculator], printer))
  def receive = {
    case "Start" => calculator ! Add(10, 12)
      calculator ! Sub(12, 10)
      calculator ! Div(5, 2)
      calculator ! Div(5, 0)
  }
}


object AllForOneStrategyApp extends App{
  val system=ActorSystem("Supervisor")
  val supervisor=system.actorOf(Props[AllForOneStrategySupervisor],"supervisor")

  supervisor ! "Start"

}