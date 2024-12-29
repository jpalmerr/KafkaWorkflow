package handler

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords}
import org.apache.kafka.common.TopicPartition

import scala.jdk.CollectionConverters._

object MockData {
  val mockdata1 = """{"_id":"368YCC2THQH1HEAQ","name":"Kiana Yoo","dob":"2021-05-31","address":{"street":"2745 Shaftsbury Circle","town":"Slough","postcode":"LS67 1ID"},"telephone":"+39-3380-033-155","pets":["Sadie","Rosie"],"score":3.7,"email":"palma14@hotmail.com","url":"http://earth.com","description":"strips rt administrators composer mumbai warranty tribunal excited halo costumes surgery series spare ticket monitored whose criminal screens enrollment range","verified":true,"salary":31900}"""
  val mockdata2 = """{"_id":"GXVRTE2G8NXQQQAG","name":"Buford Holley","dob":"2017-06-21","address":{"street":"1725 Assheton Lane","town":"St Andrews","postcode":"TA97 7IY"},"telephone":"+237-1257-580-511","pets":["Milo","Ellie"],"score":1.3,"email":"jade345@gmail.com","url":"https://secured.com","description":"bearing abroad lunch blast presidential business terminology cinema refine listprice emerging polyester allen actor george mailed geological solar behavior hub","verified":true,"salary":24210}"""
  val mockdata3 = """{"_id":"483IA2KGLMGCJRYQ","name":"Nenita Self","dob":"2023-10-10","address":{"street":"2997 Mottram","town":"Bolsover","postcode":"TD4 2MP"},"telephone":"+597-8347-765-595","pets":["Charlie","Milo"],"score":2.6,"email":"rossie-brant036@ticket.com","url":"http://trader.com","description":"forbes radical nano thy protocols provision boost sleep luis give reviews laundry naturals produced june cooked alaska pennsylvania fibre ira","verified":true,"salary":38453}"""
  val mockdata4 = """{"_id":"JDRU8X64PNYZAZH6","name":"Isaac Littlefield","dob":"2019-07-27","address":{"street":"0493 Higher","town":"Bognor Regis","postcode":"DD51 6PO"},"telephone":"+212-2726-403-510","pets":["Tigger","Buddy"],"score":1.6,"email":"hee_langlois51492@amendment.com","url":"http://www.corporation.com","description":"failures shut porsche wiki receiver talent chargers waters reductions inexpensive characteristic spelling mighty postings vat needs pattern trusts prince protecting","verified":true,"salary":29208}"""
  val mockdata5 = """{"_id":"S94NE1SQQDEY14G6","name":"Nana Tilley","dob":"2017-05-23","address":{"street":"1065 Capesthorne Road","town":"Ferryhill","postcode":"IP05 4YQ"},"telephone":"+507-5229-696-588","pets":["Nala","Bailey"],"score":3.3,"email":"alphonso-ochs4@yahoo.com","url":"https://buried.com","description":"my colleagues beats slow breeding espn discrimination link para calculated interaction dutch agrees relatively hamburg advertise broke editor characteristic adopted","verified":true,"salary":14985}"""
  val mockdata6 = """{"_id":"MAMM9FJOS8X33FY1","name":"Julia Montanez","dob":"2017-04-01","address":{"street":"0212 Drury Street","town":"Surbiton","postcode":"CF56 0NU"},"telephone":"+591-8438-103-929","pets":["Baby","Buddy"],"score":4.5,"email":"nan-ashcraft@hotmail.com","url":"http://www.delays.com","description":"beast efforts manual fleece economics compliant clusters nose letter documented attacked cite cursor refrigerator dangerous started cornwall wallpaper noise newer","verified":false,"salary":37424}"""

  // define mock data for poll response
  val record1 = new ConsumerRecord[String, String]("test-topic", 0, 0, "key0", mockdata1)
  val record2 = new ConsumerRecord[String, String]("test-topic", 0, 1, "key1", mockdata2)
  val record3 = new ConsumerRecord[String, String]("test-topic", 1, 0, "key2", mockdata3)
  val record4 = new ConsumerRecord[String, String]("test-topic", 1, 1, "key3", mockdata4)
  val record5 = new ConsumerRecord[String, String]("test-topic", 2, 0, "key4", mockdata5)
  val record6 = new ConsumerRecord[String, String]("test-topic", 2, 1, "key5", mockdata6)

  def consumerRecords(topic: String) = new ConsumerRecords[String, String](Map(
    new TopicPartition(topic, 0)-> List(record1, record2).asJava,
    new TopicPartition(topic, 1) -> List(record3, record4).asJava,
    new TopicPartition(topic, 2) -> List(record5, record6).asJava
  ).asJava)
}
