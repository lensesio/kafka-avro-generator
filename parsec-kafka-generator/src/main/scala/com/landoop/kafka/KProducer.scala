package com.landoop.kafka

import java.util.Properties
import java.util.concurrent.Future

import com.sksamuel.avro4s.{FromRecord, RecordFormat, ToRecord}
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}

class KProducer[K <: Product, V <: Product] {

  val kafkaProps = new Properties()
  kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "cloudera.landoop.com:29092")
  kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getCanonicalName)
  kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getCanonicalName)
  kafkaProps.put("schema.registry.url", "http://cloudera.landoop.com:28081")
  private lazy val producer  = new KafkaProducer[GenericRecord, GenericRecord](kafkaProps)

  def produce(topic: String, key: K, value: V, partition: Int = 0)(implicit toRecordKey: ToRecord[K], fromRecordKey: FromRecord[K], toRecord: ToRecord[V], fromRecord: FromRecord[V]): Future[RecordMetadata] = {
    val keyRec = RecordFormat[K].to(key)
    val valueRec = RecordFormat[V].to(value)
    val data: ProducerRecord[GenericRecord, GenericRecord] = new ProducerRecord(topic, partition, keyRec, valueRec)
    producer.send(data)
  }

}