package com.landoop.kafka

import java.util.{TimerTask, Timer}

import com.github.nscala_time.time.Imports._

import scala.util.{Failure, Random, Success, Try}

object Example extends App {

  case class Key(deviceId:Int)
  case class DeviceMeasurements(deviceId:Int, temperature:Int, moreData:String, timestamp:Long)



  val t = new Timer()
  t.schedule(new TimerTask() {
    @Override
    def run() {
      println("posting")
      val producer = new KProducer[Key, DeviceMeasurements]()
      produceMessages(Random.nextInt(100), producer)
    }
  }, 0, 10000);



  def produceMessages(numberOfMessages: Int, producer: KProducer[Key, DeviceMeasurements]): Unit = {
    for (a <- 1 to numberOfMessages) {
      val deviceMeasurement = getMeasurement(-10, 50)
      val deviceID = getMeasurement(0, 10)
      val timestamp = DateTime.now().getMillis

      Try(producer.produce("device-measurements-topic", Key(deviceID), DeviceMeasurements(deviceID, deviceMeasurement, "", timestamp))) match {
        case Success(m) => {

          val metadata = m.get()
          println("Success writing to Kafka topic:" + metadata.topic(),
            metadata.offset(),
            metadata.partition(),
            new DateTime(metadata.timestamp()))
        }
        case Failure(f) => println("Failed writing to Kafka",f)
      }
    }
  }

  def getMeasurement(min:Int, max:Int) = Random.nextInt(max - min) + min
}



