package kafka

import config.ServiceConfig
import handler.ProducerHandler
import loader.DataLoader.load
import model.InputModel

object KafkaProducerApp {

  def main(args: Array[String]): Unit = {
    val config = ServiceConfig.config
    // load records
    val filePath = "random-people-data.json"
    val loadRecords: Either[Throwable, InputModel] = load(filePath)

    // execute
    val producer = KafkaUtils(config).createProducer()
    val handler = ProducerHandler(producer, config.kafkaConfig.topic)
    handler.handleAndPush(loadRecords)
  }
}
