package controllers

import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.twirl.api.Html
import scala.concurrent.{ Future, ExecutionContext }
import models._

object WeatherController extends Controller {
  def index = Action { request =>
    Ok(views.html.index(Seq(
      "Bishkek",
      "Talas",
      "Naryn"
    )))
  }

  // TODO: Once you have completed `fetchWeather` and `fetchLocation` below,
  // combine the results here and feed them to the template in `report.scala.html`:
  def report(location: String) =
    Action.async { request =>
      val weather = fetchWeather(location)
      val forecast = fetchForecast(location)
      for{
        w <- weather
        f <- forecast
      } yield Ok(views.html.report(location,w,f))
      
    }

  // TODO: Complete this method. Use the WS API to gather data from the following endpoint,
  // and parse it as an instance of `models.Weather`:
  //
  // GET http://api.openweathermap.org/data/2.5/weather?q=<location>,uk
  def fetchWeather(location: String): Future[Weather] ={
    fetch[Weather]("weather",location)
  }
    

  // TODO: Complete this method. Use the WS API to gather data from the following endpoint,
  // and parse it as an instance of `models.Forecast`:
  //
  // GET http://api.openweathermap.org/data/2.5/forecast?q=<location>,uk
  def fetchForecast(location: String): Future[Forecast] =
    fetch[Forecast]("forecast",location)

  def fetch[A:Reads](endpoint:String,location:String):Future[A] ={
    println(s"http://api.openweathermap.org/data/2.5/$endpoint?q=$location,kg&APPID=4b28d05a13991becaa106f41a6512305")
    WS.url(s"http://api.openweathermap.org/data/2.5/$endpoint?q=$location,kg&APPID=4b28d05a13991becaa106f41a6512305").
    withFollowRedirects(true).
    withRequestTimeout(500).
    get().
    map(_.json.as[A])
  }
}
