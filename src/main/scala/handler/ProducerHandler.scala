package handler

import io.circe.syntax.EncoderOps
import model.InputModel
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import cats.syntax.either._

trait ProducerHandler {
  def handleAndPush(loadedRecords: Either[Throwable, InputModel])
}

object ProducerHandler {
  def apply(producer: KafkaProducer[String, String], topic: String): ProducerHandler = {
    new ProducerHandler {
      override def handleAndPush(loadedRecords: Either[Throwable, InputModel]): Unit = {
        loadedRecords
          .map { inputModel =>
            val records = inputModel.personData
            println(s"successfully loaded ${records.length} records")
            records.foreach { person =>
              val record = new ProducerRecord[String, String](topic, person.id, person.asJson.noSpaces)
              producer.send(record)
              println(s"Successfully pushed record with id ${person.id} to kafka topic $topic")
            }
            println(s"successfully pushed records onto kafka topic $topic")
            producer.close()
          }
          .leftMap(error => println(s"failed to push to kafka due to issue: ${error.getMessage}"))
          .getOrElse(())
      }
    }
  }
}
