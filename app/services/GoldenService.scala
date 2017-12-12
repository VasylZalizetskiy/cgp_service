package services

import scala.math.{log, pow, sqrt, abs}

class GoldenService {

  val cgpBase = (1 + sqrt(5)) / 2

  def getFastCode(value: Double): String = {
    val bitsCount = (log(value) / log(cgpBase)).toInt
    var amount = 0.0
    var code = ""
    for (position <- (0 to bitsCount).reverse) {
      val weightOfBit = pow(cgpBase, position)
      if (amount + weightOfBit <= value) { amount += weightOfBit; code += '1' } else code += '0'
    }
    code
  }

  def getAccurateCode(value: Double, precision: Int): String = {
    var amount = 0.0
    var code = ""
    var position = -1
    while (amount < value && position >= -precision) {
      val weightOfBit = pow(cgpBase, position)
      if (amount + weightOfBit <= value) { amount += weightOfBit; code += '1' } else code += '0'
      position-=1
    }
    code
  }

  def getDecimal(value: String): Double = {
    val codeParts = value.split('.')
    val bitsCount = codeParts(0).length
    val code = codeParts(0).reverse
    var amount = 0.0
    var amount2 = 0.0
    for (position <- 0 until bitsCount) {
      val weightOfBit = pow(cgpBase, position)
      val temp = code(position)
      if (temp == '1') amount += weightOfBit
    }
    if (codeParts.length == 2) {
      val bitsCount2 = codeParts(1).length
      val code2 = codeParts(1)
      for (position2 <- -bitsCount2 to -1) {
        val weightOfBit = pow(cgpBase, position2)
        val temp = code2(abs(position2) - 1)
        if (temp == '1') amount2 += weightOfBit
      }
    }
    amount + amount2
  }
}