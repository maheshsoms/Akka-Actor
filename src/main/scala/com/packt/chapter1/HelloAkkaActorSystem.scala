package com.packt.chapter1

object HelloAkkaActorSystem extends App {

  val actorSystem = ActorSystem("Hello-Akka")

  println(actorSystem)

}
