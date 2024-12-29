package model

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

case class Address(street: String, town: String, postcode: String)

object Address {
  implicit val aDec: Decoder[Address] = c =>
    for {
      street <- c.downField("street").as[String]
      town <- c.downField("town").as[String]
      postcode <- c.downField("postode").as[String]
    } yield Address(street, town, postcode)

  implicit val aEnc: Encoder[Address] = c =>
    Json.obj(
      "street" -> c.street.asJson,
      "town" -> c.town.asJson,
      "postcode" -> c.postcode.asJson,
    )
}

case class Person(
                   id: String,
                   name: String,
                   dob: String,
                   address: Address,
                   telephone: String,
                   pets: List[String],
                   score: Double,
                   email: String,
                   url: String,
                   description: String,
                   verified: Boolean,
                   salary: Int
                 )

object Person {
  implicit val pDec: Decoder[Person] = c =>
    for {
      id <- c.downField("_id").as[String]
      name <- c.downField("name").as[String]
      dob <- c.downField("dob").as[String]
      address <- c.downField("address").as[Address]
      telephone <- c.downField("telephone").as[String]
      pets <- c.downField("pets").as[List[String]]
      score <- c.downField("score").as[Double]
      email <- c.downField("email").as[String]
      url <- c.downField("url").as[String]
      description <- c.downField("description").as[String]
      verified <- c.downField("verified").as[Boolean]
      salary <- c.downField("salary").as[Int]
    } yield Person(id, name, dob, address, telephone, pets, score, email, url, description, verified, salary)

  implicit val pEnc: Encoder[Person] = c =>
    Json.obj(
      "_id" -> c.id.asJson,
      "name" -> c.name.asJson,
      "dob" -> c.dob.asJson,
      "address" -> c.address.asJson,
      "telephone" -> c.telephone.asJson,
      "pets" -> c.pets.asJson,
      "score" -> c.score.asJson,
      "email" -> c.email.asJson,
      "url" -> c.url.asJson,
      "description" -> c.description.asJson,
      "verified" -> c.verified.asJson,
      "salary" -> c.salary.asJson,
    )
}
