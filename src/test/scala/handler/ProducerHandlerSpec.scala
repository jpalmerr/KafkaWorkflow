package handler

import cats.implicits.catsSyntaxEitherId
import model.{Address, InputModel, Person}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.mockito.ArgumentMatcher
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import java.util.Properties
import java.util.concurrent.Future

class ProducerHandlerSpec extends AnyWordSpecLike with Matchers {
  val props = new Properties()
  val testingProducer = mock(classOf[KafkaProducer[String, String]])
  val handlerUnderTest = ProducerHandler(testingProducer, "test-topic")

  "ProducerHandler.handleAndPush" should {
    "push each person data when given valid data" in {
      val address1 = Address(street = "street1", town = "town1", postcode = "postcode1")
      val address2 = Address(street = "street2", town = "town2", postcode = "postcode2")
      val persons: List[Person] = List(
        Person(
          id = "1", name = "name1", dob = "dob1", address = address1, telephone = "tele1", pets = List("pets1"), score = 1, email = "email1", url = "url1", description = "desc1", verified = true, salary = 1
        ),
        Person(
          id = "2", name = "name2", dob = "dob2", address = address2, telephone = "tele2", pets = List("pets2"), score = 2, email = "email2", url = "url2", description = "desc2", verified = true, salary = 2
        )
      )
      val records = InputModel(persons).asRight

      // need a mock response of correct type for send method
      val mockFuture = mock(classOf[Future[RecordMetadata]])

      // mock send method
      when(testingProducer.send(any[ProducerRecord[String, String]], any())).thenReturn(mockFuture)

      // execute
      handlerUnderTest.handleAndPush(records)


      // test that send is called with expected args
      verify(testingProducer).send(argThat(new ArgumentMatcher[ProducerRecord[String, String]] {
        override def matches(record: ProducerRecord[String, String]): Boolean = {
          record.key() == "1"
        }
      }))

      verify(testingProducer).send(argThat(new ArgumentMatcher[ProducerRecord[String, String]] {
        override def matches(record: ProducerRecord[String, String]): Boolean = {
          record.key() == "2"
        }
      }))
    }

    "handle failure safely when provided with bad data" in {
      val loadedRecords = Left(new Throwable("uh oh!"))
      handlerUnderTest.handleAndPush(loadedRecords) shouldBe ()
    }
  }
}
