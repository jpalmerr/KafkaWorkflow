package handler

import cats.effect.IO
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import java.time.Duration
import scala.jdk.CollectionConverters._

trait ConsumerHandler {
  def consumeFromOffset(topic: String, offset: Long, count: Int): IO[List[Map[String, String]]]
}

object ConsumerHandler {
  def apply(consumer: KafkaConsumer[String, String]): ConsumerHandler = new ConsumerHandler {

    override def consumeFromOffset(topic: String, offset: Long, count: Int): IO[List[Map[String, String]]] = {
      IO {
        // get all partitions for the topic
        val partitions: List[Int] = consumer.partitionsFor(topic).asScala.map(_.partition()).toList

        // assign partitions
        val topicPartitions: List[TopicPartition] = partitions.map(p => new TopicPartition(topic, p))
        consumer.assign(topicPartitions.asJava)
        // seek to the specific offset
        topicPartitions.foreach(tp => consumer.seek(tp, offset))

        // poll - do this only once and then interpret results
        val pollOnce: List[ConsumerRecord[String, String]] = consumer.poll(Duration.ofMillis(5000)).asScala.toList

        // group records by partition
        val recordsByPartition: Map[Int, List[ConsumerRecord[String, String]]] = pollOnce.groupBy(_.partition())

        // interpret date - we want first 5 records FOR EACH partition
        recordsByPartition.flatMap {
          case (partition, records) =>
            // Take the first 'count' records from each partition
            records.take(count).map { record =>
              Map(
                "partition" -> partition.toString,
                "offset"    -> record.offset().toString,
                "value"     -> record.value()
              )
            }
        }.toList
      }
    }
  }
}


