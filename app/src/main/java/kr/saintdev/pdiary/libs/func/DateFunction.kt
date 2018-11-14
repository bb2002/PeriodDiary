package kr.saintdev.pdiary.libs.func

object DateFunction {
    fun getAllYears() : List<Int> = (1970 .. 2099).toList()
    fun getAllMonths() : List<Int> = (1 .. 12).toList()
}