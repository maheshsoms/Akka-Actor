package com.packt.chapter1

object BehaviourAndState extends  App {
  val actorSystem =ActorSystem("hello-akka")

  val actor=actorSystem.actorOf(Props[SummingActor],"mahesh-summing-actor")

  println(actor.path)
}
