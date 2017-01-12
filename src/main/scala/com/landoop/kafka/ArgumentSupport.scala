package com.landoop.kafka

case class ApplicationArguments(brokers: String,
                                schema: String)

trait ArgumentsSupport {

  val usage =
    """
      | kafka-avro-generator - run with arguments:
      |
      |  -brokers  127.0.0.1:9092
      |  -schema   http://127.0.0.1:8081
      |
    """.stripMargin

  def parseArgumentsOrFail(args: Array[String]): ApplicationArguments = {
    if (args.length < 4) {
      println(usage)
      sys.exit(1)
    }
    val arglist = args.toList
    type OptionMap = Map[Symbol, Any]

    def nextOption(map: OptionMap, list: List[String]): OptionMap =
      list match {
        case Nil => map
        // PARAMETERS
        case "-brokers" :: value :: tail =>
          nextOption(map ++ Map('brokers -> value.toString), tail)
        case "-schema" :: value :: tail =>
          nextOption(map ++ Map('schema -> value.toString), tail)
        case option :: tail => println("Unknown option " + option)
          nextOption(map, tail)
        // sys.exit(1)
      }

    val options: Map[Symbol, Any] = nextOption(Map(), arglist)
    validateArguments(options)

    val applicationArgs = ApplicationArguments(
      brokers = options('brokers).toString,
      schema = options('schema).toString)
    println(applicationArgs)
    applicationArgs

  }

  def validateArguments(options: Map[Symbol, Any]) = {
    val requiredConfigs = List('brokers, 'schema)

    requiredConfigs.foreach { symbol =>
      if (!options.contains(symbol)) {
        println(s"You need to supply the argument : $symbol")
        println(usage)
        sys.exit(1)
      }
    }
  }

}