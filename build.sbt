
name := "Akka-Actor"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.4"
libraryDependencies += "com.typesafe.akka" % "akka-agent_2.11" % "2.4.4"
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.17"
libraryDependencies +=  "org.scalatest" % "scalatest_2.11" % "3.0.1"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.4"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.4"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1"
libraryDependencies += "com.typesafe.akka" % "akka-persistence_2.11" % "2.4.17"
libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.7"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.17"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.6"
  
scapegoatVersion in ThisBuild := "1.3.8"


