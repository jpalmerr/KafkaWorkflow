package kafka

import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}
import config.ServiceConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import routes.ConsumerRoutes

object KafkaConsumerApp extends IOApp.Simple {
  implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

  private def kafkaConsumerResource(config: ServiceConfig): Resource[IO, KafkaConsumer[String, String]] = {
    Resource.make {
      IO {
        KafkaUtils(config).createConsumer()
      }
    } { consumer =>
      IO {
        consumer.close()
      }
    }
  }

  override def run: IO[Unit] = {
    val config = ServiceConfig.config

    kafkaConsumerResource(config).use { consumer =>
      val consumerRoutes = ConsumerRoutes(consumer)
      val httpApp = consumerRoutes.orNotFound
      EmberServerBuilder
        .default[IO]
        .withHost(config.httpConfig.host)
        .withPort(config.httpConfig.port)
        .withHttpApp(httpApp)       // attach routes
        .build
        .useForever                 // start server - keep alive
    }
  }
}
