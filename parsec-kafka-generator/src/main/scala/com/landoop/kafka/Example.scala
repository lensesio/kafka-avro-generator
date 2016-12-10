package com.landoop.kafka

import com.github.nscala_time.time.Imports._

import scala.util.{Failure, Random, Success, Try}

object Example extends App {

  case class Key(deviceId:Int)
  case class DeviceMeasurements(deviceId:Int, temperature:Int, moreData:String, timestamp:Long)

  val producer = new KProducer[Key, DeviceMeasurements]()

  for (a <- 1 to 1000) {
    val deviceMeasurement = getMeasurement(-10, 50)
    val deviceID = getMeasurement(0, 10)
    val timestamp = DateTime.now().getMillis

    Try(producer.produce("device-measurements-topic3", Key(deviceID), DeviceMeasurements(deviceID, deviceMeasurement, "", timestamp))) match {
      case Success(m) =>
        val metadata = m.get()
        println("Success writing to Kafka topic:" + metadata.topic(),
                      metadata.offset(),
                      metadata.partition(),
                      new DateTime(metadata.timestamp()))
      case Failure(f) => println("Failed writing to Kafka",f)
    }
  }

  def getMeasurement(min:Int, max:Int) = Random.nextInt(max - min) + min
}



