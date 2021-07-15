package com.klizos.blockchain.data.model


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("op")
    var op: String?,
    @SerializedName("x")
    var x: X?
)