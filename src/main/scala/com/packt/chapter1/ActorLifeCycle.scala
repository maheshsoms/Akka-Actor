package com.packt.chapter1

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._


case object Error

case class StopActor(actorRef: ActorRef)

class LifeCycleActor extends Actor {
  var sum = 1

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"sum in prerestart is $sum")
  }

  override def preStart(): Unit =
    println(s"sum in prestart is $sum")

  def receive = {
    case Error => throw new ArithmeticException()
    case _ => println("default msg")
  }

  override def postStop(): Unit = {
    println(s"sum in poststop is ${sum * 3}")
  }

  override def postRestart(reason: Throwable): Unit = {
    sum = sum * 2
    println(s"sum in postRestart is $sum")
  }

}

class SuperVisor extends Actor {
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Restart
    case t => super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
  }

  def receive = {
    case (props: Props, name: String) => sender ! context.actorOf(props, name)
    case StopActor(actorRef) => context.stop(actorRef)
  }
}

object ActorLifeCycle extends App {
  implicit val timeout = Timeout(2 seconds)

  val actorSystem = ActorSystem("LifeCycle-supervisor")
  val supervisor = actorSystem.actorOf(Props[SuperVisor], "supervisor")
  val childFuture = supervisor ? (Props(new LifeCycleActor), "LifeCycleActor")
  val child = Await.result(childFuture.mapTo[ActorRef], 2 seconds)

  child ! Error
  Thread.sleep(1000)
  supervisor ! StopActor

}



