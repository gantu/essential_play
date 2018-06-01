import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonAnimalsExercise {
  sealed trait Animal
  final case class Dog(name: String) extends Animal
  final case class Insect(legs: Int) extends Animal
  final case class Swallow(maxLoad: Int) extends Animal

  // TODO: Complete:
  //  - Create a JSON format for Animal by:
  //     - Creating JSON formats for Dog, Insect, and Swallow
  //     - Creating a JSON format for Animal that adds/uses a
  //       "type" field to distinguish between the three cases

  val dogFormat = Json.format[Dog]
  val insectFormat = Json.format[Insect]
  val swallowFormat = Json.format[Swallow]

  implicit object animalFormat extends Format[Animal]{
  	def writes(animal:Animal):JsValue = animal match {
  		case animal:Dog => dogFormat.writes(animal) ++ Json.obj("type" -> "Dog")
  		case animal:Insect => insectFormat.writes(animal) ++ Json.obj("type"->"Insect")
  		case animal:Swallow => swallowFormat.writes(animal) ++ Json.obj("type" -> "Swallow") 
  	}

  	def reads(json:JsValue):JsResult[Animal] = (json \ "type") match {
  		case JsString("Dog") => dogFormat.reads(json)
  		case JsString("Insect") => insectFormat.reads(json)
  		case JsString("Swallow") => swallowFormat.reads(json)
  		case _ => JsError(JsPath \ "type", "error.expected.animal.type") 
  	} 
  }

}