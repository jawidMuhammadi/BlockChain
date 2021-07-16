package com.klizos.blockchain.utils

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