package util

fun isNumeric(toCheck: String): Boolean {
    return toCheck.toDoubleOrNull() != null
}