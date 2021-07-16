package com.klizos.blockchain.data.model.transaction


import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("prev_out")
    var prevOut: PrevOut?,
    @SerializedName("script")
    var script: String?,
    @SerializedName("sequence")
    var sequence: Long?
)