package com.klizos.blockchain.data.model


import com.google.gson.annotations.SerializedName

data class X(
    @SerializedName("hash")
    var hash: String?,
    @SerializedName("inputs")
    var inputs: List<Input>?,
    @SerializedName("lock_time")
    var lockTime: Int?,
    @SerializedName("out")
    var `out`: List<Out>?,
    @SerializedName("relayed_by")
    var relayedBy: String?,
    @SerializedName("size")
    var size: Int?,
    @SerializedName("time")
    var time: Int?,
    @SerializedName("tx_index")
    var txIndex: Int?,
    @SerializedName("ver")
    var ver: Int?,
    @SerializedName("vin_sz")
    var vinSz: Int?,
    @SerializedName("vout_sz")
    var voutSz: Int?
)