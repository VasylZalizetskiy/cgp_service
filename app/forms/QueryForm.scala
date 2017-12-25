package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}

object QueryForm {

  val goldenCodeQuery = Form(
    mapping(
      "value" -> of[Double],
      "precision" -> optional(number)
    )(GoldenCodeQuery.apply)(GoldenCodeQuery.unapply)
  )

  val decimalQuery = Form(
    mapping(
      "code" -> nonEmptyText.verifying(cgpPattern),
    )(DecimalQuery.apply)(DecimalQuery.unapply)
  )

  val summaryQuery = Form(
    mapping(
      "code1" -> nonEmptyText.verifying(cgpPattern),
      "code2" -> nonEmptyText.verifying(cgpPattern),
    )(SummaryQuery.apply)(SummaryQuery.unapply)
  )

  case class GoldenCodeQuery(value: Double, precision: Option[Int])

  case class DecimalQuery(code: String)

  case class SummaryQuery(code1: String, code2: String)

  def cgpPattern: Constraint[String] = Constraint[String]("constraint.code") { code =>
    if (code.matches("^[01.]+")) { Valid } else { Invalid(ValidationError("Invalid code", code)) }
  }

}