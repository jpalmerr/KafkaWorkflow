package kafka

import config.ServiceConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

import java.util.Properties

// makes stubbing easier
trait KafkaUtils {
  def createProducer(): KafkaProducer[String, String]
  def createConsumer(): KafkaConsumer[String, String]
}

object KafkaUtils {
  def apply(config: ServiceConfig): KafkaUtils = {
    new KafkaUtils {
      override def createProducer(): KafkaProducer[String, String] = {
        val props = new Properties()
        props.put("bootstrap.servers", config.kafkaConfig.bootstrapServer)
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        new KafkaProducer[String, String](props)
      }

      override def createConsumer(): KafkaConsumer[String, String] = {
        val props = new Properties()
        props.put("bootstrap.servers", config.kafkaConfig.bootstrapServer)
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        props.put("group.id", "people-consumer")
        props.put("enable.auto.commit", "false") // offsets are not committed
        new KafkaConsumer[String, String](props)
      }
    }
  }
}

