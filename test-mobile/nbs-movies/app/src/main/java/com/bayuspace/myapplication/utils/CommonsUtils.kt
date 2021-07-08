package com.bayuspace.myapplication.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    return sdf.format(Date())
}
