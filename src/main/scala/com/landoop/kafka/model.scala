package com.landoop.kafka

case class Key(deviceId: Int)

case class DeviceMeasurements(deviceId: Int, temperature: Int, moreData: String, timestamp: Long)
