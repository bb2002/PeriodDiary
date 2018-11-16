package kr.saintdev.pdiary.libs.data

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * 11.14 2018 (c) 2015-2019 Saint software All rights reserved.
 * 이 객체는 일기 한 편을 구현합니다.
 */
data class DiaryObject(
    val uniqid: Int,
    val question: String,
    val content: String?,
    val date: Calendar
)

fun Calendar.makeMMDD() : String {
    return "${this.get(Calendar.MONTH) + 1}/${this.get(Calendar.DAY_OF_MONTH)}"
}

fun String.makeSQLDate() : Calendar {
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    cal.time = sdf.parse(this)
    return cal
}