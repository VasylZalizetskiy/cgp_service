package controllers

import javax.inject.Inject

import forms.QueryForm
import play.api.libs.json.Json
import play.api.mvc._
import services.GoldenProportionService

class GoldenProportionController @Inject()(goldenService: GoldenProportionService) extends InjectedController {

  private def key(els: String*) = els.mkString(".")

  def getGoldenProportionCode = Action(parse.form(QueryForm.goldenCodeQuery)) { request =>
    val data = request.body

    val result = data.precision.fold(goldenService.getFastCode(data.value)) { precision =>
      val approximateCode = goldenService.getFastCode(data.value)
      key(approximateCode, goldenService.getAccurateCode(data.value - goldenService.getDecimal(approximateCode), precision))
    }

    Ok(Json.obj("code" -> result))
  }

  def getDecimalValue = Action(parse.form(QueryForm.decimalQuery)) { request =>
    val data = request.body

    val result = goldenService.getDecimal(data.code)
    Ok(Json.obj("value" -> result))
  }

  def getSummaryCode = Action(parse.form(QueryForm.summaryQuery)) { request =>
    val data = request.body

    val result = goldenService.getSummary(data.code1,data.code2)
    Ok(Json.obj("code" -> result))
  }

}
