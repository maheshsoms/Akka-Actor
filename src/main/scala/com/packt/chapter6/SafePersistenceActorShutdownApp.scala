package com.packt.chapter6

import akka.actor.{ActorSystem, PoisonPill, Props}

object SafePersistenceActorShutdownApp extends App{
  val system=ActorSystem("safe-shutdown")

  val persistentActor1=system.actorOf(Props[SamplePersistentActor])
  val persistentActor2=system.actorOf(Props[SamplePersistentActor])


  persistentActor1 ! UserUpdate("Naai",Add)
  persistentActor1 ! UserUpdate("Delhi",Add)
  persistentActor1 ! PoisonPill

  persistentActor2 ! UserUpdate("Nalli",Add)
  persistentActor2 ! UserUpdate("Sannappa",Add)
  persistentActor2 ! ShutdownPersistentActor
}