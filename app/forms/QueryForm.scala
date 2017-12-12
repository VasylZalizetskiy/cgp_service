package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

object QueryForm {

  val getGoldenCodeQuery = Form(
    mapping(
      "value" -> of[Double],
      "precision" -> optional(number)
    )(GetGoldenCodeQuery.apply)(GetGoldenCodeQuery.unapply)
  )

  val getDecimalQuery = Form(
    mapping(
      "code" -> nonEmptyText,
    )(GetDecimalQuery.apply)(GetDecimalQuery.unapply)
  )

  case class GetGoldenCodeQuery(value: Double, precision: Option[Int])

  case class GetDecimalQuery(code: String)

}