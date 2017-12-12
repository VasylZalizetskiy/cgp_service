package controllers

import javax.inject.Inject

import forms.QueryForm
import play.api.mvc._
import services.GoldenService

class GoldenController @Inject()(goldenService: GoldenService) extends InjectedController {

  private def key(els: String*) = els.mkString(".")

  def getGoldenCode = Action(parse.form(QueryForm.getGoldenCodeQuery)) { request =>
    val data = request.body

    val inaccurateCode = goldenService.getFastCode(data.value)
    val temp = goldenService.getDecimal(inaccurateCode)
    val accurateCode = goldenService.getAccurateCode(data.value - temp, data.precision.getOrElse(20))
    Ok(key(inaccurateCode, accurateCode))

  }

  def getDecimal = Action(parse.form(QueryForm.getDecimalQuery)) { request =>
    val data = request.body

    val result = goldenService.getDecimal(data.code)
    Ok(result.toString)
  }

}
