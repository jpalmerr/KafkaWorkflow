package loader

import model.{Address, InputModel, Person}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class DataLoaderSpec extends AnyWordSpecLike with Matchers {
  "DataLoader.load" should {
    "successfully load our input file" in {
      DataLoader.load("random-people-data.json").isRight shouldBe true
    }

    "successfully map json to our input model" in {
      val input = DataLoader.load("people-sample.json")
      input shouldBe Right(
        InputModel(
          personData = List(
            Person(
              id = "368YCC2THQH1HEAQ",
              name = "Kiana Yoo",
              dob = "2021-05-31",
              address = Address(
                street = "2745 Shaftsbury Circle", town = "Slough", postcode = "LS67 1ID"
              ),
              telephone = "+39-3380-033-155",
              pets = List("Sadie", "Rosie"),
              score = 3.7,
              email = "palma14@hotmail.com",
              url = "http://earth.com",
              description = "strips rt administrators composer mumbai warranty tribunal excited halo costumes surgery series spare ticket monitored whose criminal screens enrollment range",
              verified = true,
              salary = 31900
            ),
            Person(
              id = "S94NE1SQQDEY14G6",
              name = "Nana Tilley",
              dob = "2017-05-23",
              address = Address(street = "1065 Capesthorne Road", town = "Ferryhill", postcode = "IP05 4YQ"),
              telephone = "+507-5229-696-588",
              pets = List("Nala", "Bailey"),
              score = 3.3,
              email = "alphonso-ochs4@yahoo.com",
              url = "https://buried.com",
              description = "my colleagues beats slow breeding espn discrimination link para calculated interaction dutch agrees relatively hamburg advertise broke editor characteristic adopted",
              verified = true,
              salary = 14985
            )
          )
        )
      )
    }

    "Should capture error when no valid file path" in {
      DataLoader.load("my-non-existent-file.json").isLeft shouldBe true
    }

    "Should capture error when parsing errors" in {
      DataLoader.load("corrupted-input.json").isLeft shouldBe true
    }
  }
}
