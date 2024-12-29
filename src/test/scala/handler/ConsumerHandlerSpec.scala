package handler

import cats.effect.unsafe.implicits.global
import handler.MockData._
import handler.MockData.consumerRecords
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import java.time.Duration
import scala.jdk.CollectionConverters._

class ConsumerHandlerSpec extends AnyWordSpecLike with Matchers {

  val testingConsumer = mock(classOf[KafkaConsumer[String, String]])
  val handlerUndertest = ConsumerHandler(testingConsumer)

  val topic = "test-topic"

  "ConsumerHandler.consumeFromOffset" should {
    "consume records and resolve appropriately" in {
      // mock poll to return our testing data
      when(testingConsumer.poll(Duration.ofMillis(5000))).thenReturn(consumerRecords(topic))

      // execute
      val res = handlerUndertest.consumeFromOffset(topic = topic, offset = 0, count = 2).unsafeRunSync()

      res shouldBe List(
        Map("partition" -> "0", "offset" -> "0", "value" -> mockdata1),
        Map("partition" -> "0", "offset" -> "1", "value" -> mockdata2),
        Map("partition" -> "1", "offset" -> "0", "value" -> mockdata3),
        Map("partition" -> "1", "offset" -> "1", "value" -> mockdata4),
        Map("partition" -> "2", "offset" -> "0", "value" -> mockdata5),
        Map("partition" -> "2", "offset" -> "1", "value" -> mockdata6),
      )
    }

    "correctly filter count" in {
      val topic = "test-topic"

      // mock poll to return our testing data
      val consumerRecords = new ConsumerRecords[String, String](Map(
        new TopicPartition(topic, 0)-> List(record1, record2).asJava,
        new TopicPartition(topic, 1) -> List(record3, record4).asJava,
        new TopicPartition(topic, 2) -> List(record5, record6).asJava
      ).asJava)

      when(testingConsumer.poll(Duration.ofMillis(5000))).thenReturn(consumerRecords)

      // execute
      val res = handlerUndertest.consumeFromOffset(topic = topic, offset = 0, count = 1).unsafeRunSync()

      res shouldBe List(
        Map("partition" -> "0", "offset" -> "0", "value" -> mockdata1),
        Map("partition" -> "1", "offset" -> "0", "value" -> mockdata3),
        Map("partition" -> "2", "offset" -> "0", "value" -> mockdata5),
      )
    }
  }

}
