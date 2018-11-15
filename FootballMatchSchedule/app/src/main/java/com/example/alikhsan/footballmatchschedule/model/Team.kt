package com.example.alikhsan.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("strTeamBadge")
    var strTeamBadge: String?,
    @SerializedName("strTeamJersey")
    var strTeamJersey: String?,
    @SerializedName("strTeamLogo")
    var strTeamLogo: String?


)