package config

case class KafkaConfig(
                        bootstrapServer: String,
                        topic: String,
                      )


case class ServiceConfig(
                          kafkaConfig: KafkaConfig
                        )

object ServiceConfig {
  val config: ServiceConfig =
    ServiceConfig(
      kafkaConfig = KafkaConfig(
        bootstrapServer = "localhost:9092",
        topic = "people-topic"
      )
    )
}
