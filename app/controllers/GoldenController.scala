package controllers

import javax.inject.Inject

import play.api.mvc._
import services.GoldenService

class GoldenController @Inject()(goldenService: GoldenService) extends InjectedController {

  private def key(els: String*) = els.mkString(".")

  private val precision = 4

  def getGoldenCode(value: Double) = Action {
    val inaccurateCode = goldenService.getFastCode(value)
    val temp = goldenService.getDecimal(inaccurateCode)
    val accurateCode = goldenService.getAccurateCode(value - temp, precision)

    Ok(key(inaccurateCode,accurateCode))
  }

  def getDecimal(value: String) = Action {
    val result = goldenService.getDecimal(value)
    Ok(result.toString)
  }

}
