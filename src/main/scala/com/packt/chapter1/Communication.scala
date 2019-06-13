package com.packt.chapter1

import com.packt.chapter1.messages.{Done, GiveMeRandonNumber, Start}




  object messages {
    case class Done(radnomNumber: Int)
    case object GiveMeRandonNumber
    case class Start(actorRef: ActorRef)
  }

class RandomNumberGeneratorActor extends Actor {
  override def receive: Receive = {
    case GiveMeRandonNumber => println("recieved a message to generate a random number")
    val randomNumber = nextInt
      sender ! Done(randomNumber)
  }
}
class QueryActor extends Actor{
 import messages._
  override def receive: Receive = {

    case Start(actorRef) => println(s"send me the next random number")
      actorRef ! GiveMeRandonNumber
    case Done(randomNumber) => println(s"recived a random number $randomNumber")
  }
}

object Communication extends App{
  val actorSystem=ActorSystem("Hello-RandomNumber")
  val randomNumberGenerator=actorSystem.actorOf(Props[RandomNumberGeneratorActor],"randomNumberGenerator")
  val queryActor =actorSystem.actorOf(Props[QueryActor],"queryActor")

  queryActor ! Start(randomNumberGenerator)
}
