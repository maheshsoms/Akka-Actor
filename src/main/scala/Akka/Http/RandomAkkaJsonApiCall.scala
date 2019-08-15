package Akka.Http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpMethods, HttpRequest}
import spray.json.DefaultJsonProtocol
import spray.json.JsonFormat

class RandomAkkaJsonApiCall(implicit val actorSystem: ActorSystem) extends DefaultJsonProtocol {

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

  val endpoint="https://randomuser.me/api/?results=5"

  case class Foo(i: Int, foo: Foo)
  implicit val fooFormat: JsonFormat[Foo] = lazyFormat(jsonFormat(Foo, "i", "foo")(IntJsonFormat,fooFormat: JsonFormat[Foo]))


  implicit val inf =jsonFormat4(Info)
  implicit val nam=jsonFormat3(Name)
  implicit val cor=jsonFormat2(Coordinates)
  implicit val tz=jsonFormat2(Timezone)
  implicit val loc=jsonFormat6(Location)
  implicit val dob=jsonFormat2(Dob)
  implicit val login=jsonFormat7(Login)
  implicit val id= jsonFormat2(Id)
  implicit val pic=jsonFormat3(Picture)

  implicit val rand=jsonFormat12(Results)

  implicit val random: JsonFormat[Seq[Results]] = lazyFormat(jsonFormat(RootProfile,"results","info")(seqFormat(rand),inf))


  def createRequest(): HttpRequest = HttpRequest(method = HttpMethods.GET,uri = endpoint,entity = HttpEntity(ContentTypes.`application/json`,rand.toString))

}
