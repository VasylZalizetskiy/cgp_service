package controllers

import javax.inject.Inject

import forms.QueryForm
import play.api.libs.json.Json
import play.api.mvc._
import services.GoldenService

class GoldenController @Inject()(goldenService: GoldenService) extends InjectedController {

  private def key(els: String*) = els.mkString(".")

  def getGoldenCode = Action(parse.form(QueryForm.getGoldenCodeQuery)) { request =>
    val data = request.body

    val result = data.precision.fold(goldenService.getFastCode(data.value)) { precision =>
      val approximateCode = goldenService.getFastCode(data.value)
      key(approximateCode, goldenService.getAccurateCode(data.value - goldenService.getDecimal(approximateCode), precision))
    }

    Ok(Json.obj("code" -> result))
  }

  def getDecimal = Action(parse.form(QueryForm.getDecimalQuery)) { request =>
    val data = request.body

    val result = goldenService.getDecimal(data.code)
    Ok(Json.obj("value" -> result))
  }

}
