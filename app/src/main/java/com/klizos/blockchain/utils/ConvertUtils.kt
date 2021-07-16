package com.klizos.blockchain.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ahmad Jawid Muhammadi
 * on 16-07-2021.
 */


fun getBitcoinPrice(satoshi: Int): Double {
    val bitcoin = convertSatoshiToBitcoin(satoshi)
    return bitcoin * BITCOIN_TO_USD
}

private fun convertSatoshiToBitcoin(satoshi: Int): Float {
    return (satoshi / BITCOIN_TO_SATOSHI_RATE).toFloat()
}

fun getTimeInIST(timeInMillis: Int, format: String?): String? {
    val date = getDateFromTimeMillis(
        timeInMillis,
        DATE_FORMAT
    )
    TimeZone.setDefault(TimeZone.getTimeZone("IST"))
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"))
    calendar.timeInMillis = date!!.time * 1000
    val outputFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return outputFormat.format(calendar.timeInMillis)
}

private fun getDateFromTimeMillis(timeInMillis: Int, format: String?): Date? {
    val stringDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    stringDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = Date()
    date.time = timeInMillis.toLong()
    return date
}
