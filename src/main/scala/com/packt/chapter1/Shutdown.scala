package com.packt.chapter1

case object Stop

class ShutdownActor extends Actor {

  override def receive: Receive = {
    case msg: String => println(s"$msg")
    case Stop => context.stop(self)
  }
}

object Shutdown extends App{
  val actorSystem =ActorSystem("ShutDown")
  val shutDownActor1=actorSystem.actorOf(Props[ShutdownActor])

  shutDownActor1 ! "hello"
  shutDownActor1 ! PoisonPill
  shutDownActor1 ! "Are you there"

  val shutdownActor2=actorSystem.actorOf(Props[ShutdownActor])

  shutdownActor2 ! "hello"
  shutdownActor2 ! Stop
  shutdownActor2 ! "are you there"
}