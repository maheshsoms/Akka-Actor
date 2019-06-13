package com.packt.chapter1

case object CreateChild
case class Greet(msg: String)

class ChildActor extends Actor {
  def receive = {
    case Greet(msg) => println(s"my parent[${self.path.parent}] greeted to me [${self.path}]")
  }
}
class ParentActor extends Actor{
  def receive = {
    case CreateChild =>
      val child=context.actorOf(Props[ChildActor])
      child ! Greet("hello Child")
  }
}

object ParentChild extends App {
  val actorSystem=ActorSystem("ParentChild-Actor")
  val parent=actorSystem.actorOf(Props[ParentActor])

  parent ! CreateChild
}