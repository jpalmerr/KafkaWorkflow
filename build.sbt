import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "KafkaWorkflow"
  )

Compile / run / fork := true

val circeVersion = "0.14.10"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "3.7.0",
  "io.circe"      %% "circe-core" % circeVersion,
  "io.circe"      %% "circe-parser" % circeVersion,
  "io.circe"      %% "circe-generic" % circeVersion,
  "com.lihaoyi"   %% "os-lib" % "0.11.2",
  "org.typelevel" %% "cats-core" % "2.11.0",
  "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
  "org.scalatest" %% "scalatest" % "3.2.19",
  "org.mockito" % "mockito-core" % "5.3.0",
  "ch.qos.logback" % "logback-classic" % "1.4.11",
)