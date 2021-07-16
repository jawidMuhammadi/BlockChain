package com.klizos.blockchain.data.model.transaction


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("op")
    var op: String?,
    @SerializedName("x")
    var x: X?
)