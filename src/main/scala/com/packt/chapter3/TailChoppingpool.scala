package com.packt.chapter3

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.TailChoppingPool
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._

class TailChoppingActor extends Actor {
  override def receive = {
    case msg: String => sender ! "I say hello back to you"
    case _ => println("I dont understand the message")
  }
}

object ScatterGatherFirstCompletedpool1 extends App{
  implicit val timeout = Timeout(10 seconds)
  val actorSystem=ActorSystem("Hello-Akka")
  val router = actorSystem.actorOf(TailChoppingPool(5,
    within = 10.seconds,interval = 20.millis).
    props(Props[TailChoppingActor]))
  println(Await.result((router ? "hello").mapTo[String], 10
    seconds))
}