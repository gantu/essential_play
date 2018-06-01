package controllers
import scala.concurrent.Future

import org.scalatestplus.play._



import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat

class ApplicationSpec extends PlaySpec with Results {

  "time endpoint" must {
    "should give local time" in {
      val controller = new TimeController(stubControllerComponents())
      val result:Future[Result] = controller.time().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      val time = DateTime.now
      val localTime = DateTimeFormat.shortTime.print(time)
      bodyText must include(localTime)
    }

    "should give local time given zone" in {
      val controller = new TimeController(stubControllerComponents())
      val result:Future[Result] = controller.timeIn("Asia/Almaty").apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      val time = DateTime.now
      val localTime = DateTimeFormat.shortTime.print(time)
      bodyText must include(localTime)
    }

    "should give list of zones" in{
      val controller = new TimeController(stubControllerComponents())
      val result:Future[Result] = controller.zones().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      bodyText must include("""Europe/Berlin""")
    }

    "should return 404 on POST" in{
      val controller = new TimeController(stubControllerComponents())
      val result:Future[Result] = controller.time().apply(FakeRequest(POST,"/time"))
      status(result) must be("404")
    }
  }

}
