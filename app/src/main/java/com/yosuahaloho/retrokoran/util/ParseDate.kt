package com.yosuahaloho.retrokoran.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Yosua on 22/05/2023
 */
fun parseDate(originalDate: String, originalFormat: String, targetFormat: String): String {
    val originalDateFormat = SimpleDateFormat(originalFormat, Locale.getDefault())
    val targetDateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())

    val parsedDate = originalDateFormat.parse(originalDate)
    return targetDateFormat.format(parsedDate!!)
}