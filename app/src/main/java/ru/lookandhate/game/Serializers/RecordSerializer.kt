package ru.lookandhate.game.Serializers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class RecordSerializer {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("points")
    @Expose
    var points = 0

    @SerializedName("date")
    @Expose
    var date = 0
}