package services

import scala.collection.mutable.ArrayBuffer
import scala.math.{abs, log, pow, sqrt}

class GoldenProportionService {

  private val cgpBase = BigDecimal((1 + sqrt(5)) / 2).setScale(14, BigDecimal.RoundingMode.HALF_DOWN).toDouble

  private def fromJuniorBits[T](bits: Seq[T]) = bits.reverse

  def getDecimal(code: String): Double = {
    val codeParts = code.split('.')
    val fastCode = fromJuniorBits(codeParts(0))
    val fastBitsCount = fastCode.length
    var fastAmount = 0.0
    var accurateAmount = 0.0
    for (position <- 0 until fastBitsCount) {
      val weightOfBit = pow(cgpBase, position)
      val temp = fastCode(position)
      if (temp == '1') fastAmount += weightOfBit
    }
    if (codeParts.length == 2) {
      val accurateCode = codeParts(1)
      val accurateBitsCount = accurateCode.length
      for (position <- -accurateBitsCount to -1) {
        val weightOfBit = pow(cgpBase, position)
        val temp = accurateCode(abs(position) - 1)
        if (temp == '1') accurateAmount += weightOfBit
      }
    }
    fastAmount + accurateAmount
  }

  def getFastCode(value: Double): String = {
    val bitsCount = (log(value) / log(cgpBase)).toInt
    var amount = 0.0
    var code = ""
    for (position <- fromJuniorBits(0 to bitsCount)) {
      val weightOfBit = pow(cgpBase, position)
      if (amount + weightOfBit <= value) {
        amount += weightOfBit
        code += '1'
      } else code += '0'
    }
    code
  }

  def getAccurateCode(value: Double, precision: Int): String = {
    var position = -1
    var code = ""
    var amount = 0.0
    if (precision > 0) {
      while (amount < value && position >= -precision) {
        val weightOfBit = pow(cgpBase, position)
        if (amount + weightOfBit <= value) {
          amount += weightOfBit
          code += '1'
        } else code += '0'
        position -= 1
      }
    }
    else if (precision == -1) {
      while (amount < value) {
        val weightOfBit = pow(cgpBase, position)
        if (amount + weightOfBit <= value) {
          amount += weightOfBit
          code += '1'
        } else code += '0'
        position -= 1
      }
    }
    else code += '0'
    code
  }

  def convolution(code: String): String = {
    val codeParts = code.split('.')
    val fastCode = fromJuniorBits(codeParts(0))
    val fastBitsCount = fastCode.length
    if (fastBitsCount > 1) {
      var resultCode = ArrayBuffer[Char]()
      var position = 0
      while (position < fastBitsCount) {
        val nextPosition = position + 1
        val resPosition = nextPosition + 1
        if (fastCode(position) == '1' && fastCode(nextPosition) == '1') {
          resultCode.insert(position, '0','0','1')
        }
        else {
          resultCode.insert(position, fastCode(position), fastCode(nextPosition), fastCode(resPosition))
        }
        position=resPosition
      }
      resultCode.mkString
    }
    else code

  }

  def getSummary(code1: String, code2: String): String = {
    convolution(code1)
  }

}
