package cheatsheets

data class Measurement(val type: String, val value: Int)
data class Temperature(val value: Int)

sealed class Season
object Spring : Season()
object Summer : Season()
object Autumn : Season()
object Winter : Season()

