package com.example.alikhsan.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventTeam(
   @SerializedName("idEvent")
   var eventId :String? ,
   @SerializedName("strHomeTeam")
   var homeTeamName : String?,
   @SerializedName("strAwayTeam")
   var awayTeamName : String?,

   @SerializedName("idHomeTeam")
   var homeTeamId : String?,
   @SerializedName("idAwayTeam")
   var awayTeamId : String?,

   @SerializedName("intHomeScore")
   var homeTeamScore : String?,
   @SerializedName("intAwayScore")
   var awayTeamScore : String?,

   @SerializedName("dateEvent")
   var eventDate : String?







) : Parcelable
