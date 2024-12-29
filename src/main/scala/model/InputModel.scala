package model

import io.circe.Decoder

final case class InputModel(personData: List[Person])

object InputModel {
  implicit val iDec: Decoder[InputModel] = c => {
    for {
      persons <- c.downField("ctRoot").as[List[Person]]
    } yield InputModel(persons)
  }
}
