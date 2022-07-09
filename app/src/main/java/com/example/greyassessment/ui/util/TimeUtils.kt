package com.example.greyassessment.ui.util
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.Year
import java.time.temporal.ChronoField

@RequiresApi(Build.VERSION_CODES.O)
fun String.getDate(): String {


    val date: LocalDateTime = LocalDateTime.parse(this.dropLast(1))
    val dateYear = date.get(ChronoField.YEAR)
    val year: Comparable<*> = if(Year.now().value == dateYear) "" else dateYear


    return("Last Updated ${date.get(ChronoField.DAY_OF_MONTH)} ${date.get(ChronoField.MONTH_OF_YEAR)} $year")



}