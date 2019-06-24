package com.packt.chapter4

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class FutureActor extends Actor {

  import context.dispatcher

  def receive = {
    case (a: Int, b: Int) => val f = Future(a + b)
      (Await.result(f, 10 seconds))
      println(f.mapTo[Int])

  }
}


object FutureInsideActor extends App {
  val actorSystem = ActorSystem("Hello-Akka")
  val fActor = actorSystem.actorOf(Props[FutureActor])
  fActor ! (10, 20)

}

