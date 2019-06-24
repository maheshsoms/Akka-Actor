package com.packt.chapter4

import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ReducingFutures extends App {

  val timeout = Timeout(10 seconds)
  val listofFutures = (1 to 10).map(Future(_))
  val finalFuture = Future.reduce(listofFutures)(_ + _)
  println(s"sum of numbers from 1 to 10 is ${Await.result(finalFuture, 10 seconds)}")
}
