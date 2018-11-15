package com.example.alikhsan.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class DetailEvent(
    @SerializedName("intHomeShots")
    var homeShots: String?,
    @SerializedName("intAwayShots")
    var awayShots: String?,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalKeeper: String?,
    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalKeeper: String?,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String?,
    @SerializedName("strAwayLineupDefense")
    var awayDefense: String?,

    @SerializedName("strHomeLineupMidfield")
    var homeMidField: String?,
    @SerializedName("strAwayLineupMidfield")
    var awayMidField: String?,

    @SerializedName("strHomeLineupForward")
    var homeForward: String?,
    @SerializedName("strAwayLineupForward")
    var awayForward: String?,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubtitutes: String?,
    @SerializedName("strAwayLineupSubstitutes")
    var awaySubtitutes: String?,

    @SerializedName("strHomeGoalDetails")
    var homeGoalDetail : String?,
    @SerializedName("strAwayGoalDetails")
    var awayGoalDetail:String?
)