package com.packt.chapter1

class BecomeUnBecomeActor extends Actor {
  def receive: Receive = {
    case true => context.become(isStateTrue)
    case false => context.become(isStateFalse)
    case _ => println("i dont know what you want to say")
  }

  def isStateTrue: Receive = {
    case msg: String => println(msg)
    case false => context.become(isStateFalse)
  }

  def isStateFalse: Receive = {
    case msg: Int => println(s"$msg")
    case true => context.become(isStateTrue)
  }

}
