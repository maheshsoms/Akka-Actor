package com.packt.chapter1

import akka.actor.{ActorSystem, Props}

object BecomUnBecomeApp extends App {
  val actorSystem = ActorSystem("State-Actor")
  val stateActor = actorSystem.actorOf(Props[BecomeUnBecomeActor])

  stateActor ! true
  stateActor ! "hello how are you"
  stateActor ! false
  stateActor ! "my name is mahesh"
  stateActor ! true
  stateActor ! "after this nothing will be processed"
  stateActor ! false
  stateActor ! "some random shit"
}
