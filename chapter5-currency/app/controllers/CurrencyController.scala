package controllers

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._

object CurrencyController extends Controller with ExchangeRateHelpers {
  def convertOne(fromAmount: Double, fromCurrency: Currency, toCurrency: Currency) =
    Action.async { request =>
      // TODO:
      //  - Convert fromAmount to USD using ExchangeRateHelpers.toUSD
      //  - Convert the USD amount to toCurrency using ExchangeRateHelpers.fromUSD
      //  - Format the result using formatConversion
      
      val output:Future[String] = for{
        usd <- toUSD(fromAmount,fromCurrency)
        expectedAmount <- fromUSD(usd, toCurrency)
      } yield formatConversion(fromAmount,fromCurrency,expectedAmount,toCurrency)

      output.map(Ok(_))
    }

  def convertAll(fromAmount: Double, fromCurrency: Currency) =
    Action.async { request =>
      // TODO:
      //  - For all toCurrency in ExchangeRateHelpers.currencies:
      //     - Convert fromAmount to USD using ExchangeRateHelpers.toUSD
      //     - Convert the USD amount to toCurrency using ExchangeRateHelpers.fromUSD
      //     - Format the result using formatConversion
      //  - Combine all results into a single plain text response
      val ouptut:Seq[Future[String]] = currencies.map{ toCurrency:Currency =>
        for{
          usdAmount <- toUSD(fromAmount,fromCurrency)
          toAmount <- fromUSD(usdAmount,toCurrency)
        } yield formatConversion(fromAmount,fromCurrency,toAmount,toCurrency)
      }

      Future.sequence(ouptut).map(lines => Ok(lines mkString "\n"))
    }
}

trait ExchangeRateHelpers {
  val currencies: Seq[Currency] =
    Seq(USD, GBP, EUR)

  def toUSD(amount: Double, from: Currency): Future[Double] =
    from match {
      case USD => Future.successful(amount * 1.0)
      case GBP => Future.successful(amount * 1.5)
      case EUR => Future.successful(amount * 1.1)
    }

  def fromUSD(amount: Double, to: Currency): Future[Double] =
    toUSD(1.0, to) map (amount / _)

  def formatConversion(fromAmount: Double, fromCurrency: Currency, toAmount: Double, toCurrency: Currency): String =
    Currency.format(fromAmount, fromCurrency) + " = " + Currency.format(toAmount, toCurrency)
}
