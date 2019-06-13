package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.{DefaultResizer, RoundRobinPool}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.Await

case object Load
case class sum(n: Int)

class LoadActor extends Actor {
  def receive = {
    case Load =>
      println("Handling loads of request")

    case n:Int => {
      val line=fib(n)
      sender ! line
      }
      def fib(n: Int): List[Int] = n match {
        case 0 => Nil
        case _ => List.fill(n)(4)
      }
    }
  }


object ResizablePool extends App {
  val actorSystem=ActorSystem("Resizable-pool")
  implicit val timeout = Timeout(10 seconds)
  val resizer =DefaultResizer(2,15)
  val router=actorSystem.actorOf(RoundRobinPool(5,Some(resizer)).props(Props[LoadActor]))
  val future=(router ? 10)
  val list=Await.result(future,10 seconds)
  router ! Load
println(list)
}