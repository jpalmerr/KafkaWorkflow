package routes

import cats.effect.IO
import handler.ConsumerHandler
import io.circe.syntax._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.io._

object ConsumerRoutes {

  object OffsetQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Int]("offset")
  object CountQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Int]("count")

  def apply(consumer: KafkaConsumer[String, String]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "topic" / topicName :? OffsetQueryParamMatcher(offset) :? CountQueryParamMatcher(count) =>
      val records = ConsumerHandler(consumer).consumeFromOffset(topic = topicName, offset = offset.getOrElse(0).toLong, count = count.getOrElse(10))
      records.flatMap { result =>
        Ok(result.asJson)
      }
  }
}

