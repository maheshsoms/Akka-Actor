package com.packt.chapter5

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.{CircuitBreaker, ask}
import akka.util.Timeout

import scala.concurrent.duration._
case class FetchRecord(recordID: Int)
case class Person(name: String,age: Int)

object DB {
  val data=Map(1 -> Person("Mahesh",28),2-> Person("Suresh",35),3->Person("Somasundaram",66),4->Person("Gomathi",55),6->Person("Pragathi",3))
}

class DBActor extends Actor  with ActorLogging{
  def receive = {
    case FetchRecord(recordID) =>
      if(recordID>=3 && recordID <=5)
        Thread.sleep(3000)
      else sender ! DB.data.get(recordID)
      log.info("i will process after the time out set of 2 seconds")
  }
}


object CirucuitBreackerApp extends App{
  val system=ActorSystem("Circuit-Breaker")
  implicit val ec=system.dispatcher
  implicit val timeout=Timeout(3 seconds)

  val breaker=new CircuitBreaker(system.scheduler,3,1 seconds,2 seconds).onOpen(println("=======state is open ================="))
    .onClose(println("=====================state is closed ================"))

  val db=system.actorOf(Props[DBActor],"DBActor")

  (1 to 10).map(recordID => {
    Thread.sleep(3000)
    val askFuture=breaker.withCircuitBreaker(db ? FetchRecord(recordID))
    askFuture.map(record => s"Record is: $record and RecordID $recordID").recover({
      case fail => "Failed with:"+fail.toString
    }).foreach(x=>println(x))
  })
}
