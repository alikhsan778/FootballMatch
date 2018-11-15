package com.example.alikhsan.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavTeam(
    val mId: Int?,
    val mIdEvent: String?,
    val mDateEvent: String?,
    val mIdTeamHome: String?,
    val mIdTeamAway: String?,
    val mTeamHomeName: String?,
    val mTeamAwayName: String?,
    val mScoreTeamHome: String?,
    val mScoreTeamAway: String?,
    val mImageTeamHome: String?,
    val mImageTeamAway: String?
) : Parcelable