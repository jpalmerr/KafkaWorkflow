package kafka

import config.ServiceConfig
import org.apache.kafka.clients.producer.KafkaProducer

import java.util.Properties

// makes stubbing easier
trait KafkaUtils {
  def createProducer(): KafkaProducer[String, String]
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
    }
  }
}
