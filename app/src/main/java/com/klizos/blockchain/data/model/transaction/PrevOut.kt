package com.klizos.blockchain.data.model.transaction


import com.google.gson.annotations.SerializedName

data class PrevOut(
    @SerializedName("addr")
    var addr: String?,
    @SerializedName("n")
    var n: Int?,
    @SerializedName("script")
    var script: String?,
    @SerializedName("spent")
    var spent: Boolean?,
    @SerializedName("tx_index")
    var txIndex: Int?,
    @SerializedName("type")
    var type: Int?,
    @SerializedName("value")
    var value: Int?
)