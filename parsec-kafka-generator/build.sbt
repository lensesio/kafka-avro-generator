name := "parsec-kafka-generator"

version := "1.0"

scalaVersion := "2.11.8"

val kafkaVersion = "0.10.0.0"
val confluentVersion = "3.0.1"

libraryDependencies ++= {
  Seq(
    "com.github.nscala-time" %% "nscala-time" % "2.14.0",
    "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.2",
    "org.apache.kafka" %% "kafka" % "0.10.0.1",
    "io.confluent" % "kafka-avro-serializer" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  "Confluent Maven Repo" at "http://packages.confluent.io/maven/"
)

mainClass := Some("com.landoop.kafka.Example")