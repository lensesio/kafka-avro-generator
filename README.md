# Landoop blogs code examples

- Time-Series with Kafka, Kafka Connect & InfluxDB [blog](http://www.landoop.com/blog/2016/12/kafka-influxdb/)

## Execute

```bash
sbt run
```

To connect to the brokers, zookeepers and schema-registry as defined in `application.conf`

## Build

```bash
sbt clean compile
```

To test

```bash
sbt clean test
```

To view dependency tree

```bash
sbt dependencyTree
```

To create a fat jar

```bash
sbt assembly
```

## Deploy

Build an assembly and define the Brokers and the Schema Registry

    sbt assembly

    java -jar target/scala-2.11/avro-kafka-generator-assembly-1.0.jar \
         -brokers 127.0.0.1:9092 \
         -schema http://127.0.0.1:8081
