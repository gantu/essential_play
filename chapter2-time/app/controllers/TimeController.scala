package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._

import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat
import javax.inject._

@Singleton
class TimeController @Inject()(cc:ControllerComponents) extends AbstractController(cc) with TimeHelpers {
  // TODO: Return an HTTP 200 plain text response containing the time.
  //
  // Use the `localTime` and `timeToString` helper methods below.
  def time = Action{request=>
    Ok(timeToString(localTime))
  }

  // TODO: Read in a time zone ID (a string) and return an HTTP 200
  // plain text response containing the localized time.
  //
  // Use the `localTimeInZone` and `timeToString` helper methods below.
  def timeIn(zoneId: String) = Action{request=>
    val time = localTimeInZone(zoneId)
    Ok(time map timeToString getOrElse "Time zone not recognized")
  }

  // TODO: Return an HTTP 200 plain text response containing a list of
  // available time zone codes.
  //
  // Use the `zoneIds` helper method below.
  def zones = Action{request =>
    Ok(zoneIds mkString "\n")
  }
}

trait TimeHelpers {
  def localTime: DateTime =
    DateTime.now

  def localTimeInZone(zoneId: String): Option[DateTime] =
    zoneForId(zoneId) map (DateTime.now.withZone)

  def timeToString(time: DateTime): String =
    DateTimeFormat.shortTime.print(time)

  def zoneIds: List[String] = {
    import scala.collection.JavaConversions._
    DateTimeZone.getAvailableIDs.toList
  }

  def zoneForId(zoneId: String): Option[DateTimeZone] =
    try { Some(DateTimeZone.forID(zoneId)) }
    catch { case exn: IllegalArgumentException => None }
}
