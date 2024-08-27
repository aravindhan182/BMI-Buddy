package util

import kotlin.math.pow
import kotlin.math.roundToInt

fun isNumeric(toCheck: String): Boolean {
    return toCheck.toDoubleOrNull() != null
}

fun Float.roundTo(numFractionDigits: Int): Float {
    val factor = 10.0.pow(numFractionDigits.toDouble()).toFloat()
    return (this * factor).roundToInt() / factor
}