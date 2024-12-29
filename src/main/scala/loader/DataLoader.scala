package loader

import io.circe.parser
import model.InputModel

import scala.util.Try

object DataLoader {
  def load(filePath: String): Either[Throwable, InputModel] = {
    val fileContent = for {
      fileName <- Try {os.read(os.resource / filePath)}.toEither
      parsed   <- parser.parse(fileName)
    } yield parsed

    fileContent.flatMap { json =>
      json.as[InputModel] match {
        case Left(decodingFailure) => Left(new Throwable(decodingFailure.getMessage()))
        case Right(rr) => Right(rr)
      }
    }
  }
}
