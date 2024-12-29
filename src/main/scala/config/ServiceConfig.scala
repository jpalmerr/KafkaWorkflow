package config

import com.comcast.ip4s.{Hostname, Port}

case class KafkaConfig(
                        bootstrapServer: String,
                        topic: String,
                      )

case class HttpConfig(
                       host: Hostname,
                       port: Port
                     )

case class ServiceConfig(
                          kafkaConfig: KafkaConfig,
                          httpConfig: HttpConfig
                        )

object ServiceConfig {
  val config: ServiceConfig =
    ServiceConfig(
      kafkaConfig = KafkaConfig(
        bootstrapServer = "localhost:9092",
        topic = "people-topic"
      ),
      httpConfig = HttpConfig(
        host = Hostname.fromString("0.0.0.0").get,
        port = Port.fromInt(8080).get
      )
    )
}