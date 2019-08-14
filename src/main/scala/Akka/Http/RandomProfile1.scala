package Akka.Http


import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{ read }
import org.json4s.NoTypeHints


  import dispatch._
  import Defaults._

  object RandomProfile1 {


    def main(args: Array[String]) {

      //Step 1 : Prepare the request object

      //even though its a https . doing a .secure is not required
      val request = url("https://randomuser.me/api/?page=3&results=10")
      val requestAsGet = request.GET //not required but lets be explicit

      //Step 2 : Set the required parameters
      val builtRequest = requestAsGet

      //Step 3: Make the request (method is already set above)
      val content = Http.default(builtRequest)

      //Step 4: Once the response is available
      //response completed successfully
      content onSuccess {

        //Step 5 : Request was successful & response was OK
        case x if x.getStatusCode() == 200 =>
          //Step 6 : Response was OK, read the contents
          handleJsonOutput(x.getResponseBody)
        case y => //Step 7 : Response is not OK, read the error
          println("Failed with status code" + y.getStatusCode())
      }

      //Step 7 : Request did not complete successfully, read the error
      content onFailure {
        case x =>
          println("Failed but"); println(x.getMessage)
      }

      println("ENTER TO EXIT")
      scala.io.StdIn
    }


  case class Coordinates (latitude: String, longitude: String)
  case class Dob (date: String, age: Int)
  case class Id (name: String, value: String)
  case class Info (seed: String, results: Int, page: Int, version: String)
  case class Location (street: String, city: String, state: String, postcode: String, coordinates: Coordinates, timezone: Timezone)
  case class Login (uuid: String, username: String, password: String, salt: String, md5: String, sha1: String, sha256: String )
  case class Name (title: String, first: String, last: String)
  case class Picture (large: String, medium: String, thumbnail: String)
  case class Results (gender: String, name: Name, location: Location, email: String, login: Login, dob: Dob, registered: Dob, phone: String, cell: String, id: Id, picture: Picture, nat: String)
  case class RootProfile (results: Seq[Results], info: Info)
  case class Timezone (offset: String, description: String)

private def handleJsonOutput(body: String): Unit = {
  //required to set implicit here for JSON serialization to work properly
  implicit val formats = Serialization.formats(NoTypeHints)

  //read the output as a RootJsonObject instance
  //the read call does the trick of mapping JSon to the case classes
  val output = read[RootProfile](body)
  println(s"Total Results: ${output.results.size}")

  for (person <- output.results) {
    println(s"$person + ${output.info} ")

  }
}
}