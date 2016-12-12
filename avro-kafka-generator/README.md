
# Simple Kafka Avro Producer with example

This is the code example for the blog post: 
http://www.landoop.com/blog/2016/12/kafka-influxdb/

When running it will produce the synthetic messages into Kafka and create the schema registry entries for the key and the value. 

#### Configure
In KProducer update the urls if not running in localhost: 
```
kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
//...
kafkaProps.put("schema.registry.url", "http://127.0.0.1:8081")
```
This example uses Avro Serializer for both the key and the value:
```
kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getCanonicalName)
kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getCanonicalName)
```
This example uses `avro4s` to generate the schema, so we only need to create the case classes: 
```
case class Key(deviceId:Int)
case class DeviceMeasurements(deviceId:Int, temperature:Int, moreData:String, timestamp:Long)`
```
#### Run
Just run: `sbt run`
