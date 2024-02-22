package com.example.mymovieapp.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun formatSimpleDate(value: String) : String {
    if(value == "") return ""

    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    val date = parser.parse(value)

    return formatter.format(date)
}

fun formatIsoDateToDateTime(value: String): String {
    if(value == "") return ""

    val dateTime = DateTime(value)
    val dtf = DateTimeFormat.forPattern("MMM dd, yyyy - HH:mm")

    return dtf.print(dateTime)
}