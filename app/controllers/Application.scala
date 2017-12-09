package controllers

import javax.inject.Inject
import play.api.mvc._
import scala.math.{sqrt, log, pow}

class Application extends InjectedController {

  val cgpBase = (1 + sqrt(5)) / 2

  def toGoldenProportionCode(value: Double) = Action {
    val bitsCount = (log(value) / log(cgpBase)).toInt
    var sum = 0.0
    var result = ""
    for (position <- (0 to bitsCount).reverse) {
      val weightOfBit =pow(cgpBase, position)
      if (sum + weightOfBit <= value) { sum += weightOfBit; result+='1' } else result+='0'
    }
    Ok(result)
  }

  def toDecimal(value: String) = Action {
    val bitsCount = value.size
    val code = value.reverse
    var sum = 0.0
    for (position <- (0 until bitsCount)) {
      val weightOfBit = pow(cgpBase, position)
      val temp = code(position)
      if (temp == '1') sum += weightOfBit
    }
    Ok(sum.toString)
  }

}
