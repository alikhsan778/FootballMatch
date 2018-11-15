package com.example.alikhsan.footballmatchschedule.network

import android.net.Uri
import com.example.alikhsan.footballmatchschedule.BuildConfig

object SpotDBApi {
    const val BASE_URL = BuildConfig.BASE_URL
    const val API_KEY = BuildConfig.API_KEY

    fun getEventFootball(mId: String?, mEvent: String?): String {
        return Uri.parse(BASE_URL).buildUpon()
            .appendPath(API_KEY)
            .appendPath(mEvent)
            .appendQueryParameter("id", mId)
            .build().toString()
    }

    fun getGambarBadges(mTeamId:String?):String{
        return Uri.parse(BASE_URL).buildUpon()
            .appendPath(API_KEY)
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", mTeamId)
            .build().toString()
    }

    fun getDetailEvent(mEventId : String?) : String{
        return Uri.parse(BASE_URL).buildUpon()
            .appendPath(API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id",mEventId)
            .build().toString()
    }

    fun getDetailMatch(teamId:String?) : String{
        return BASE_URL + API_KEY+ "lookupteam.php?id=" + teamId
    }

    fun getDetailMatchEvent(eventId: String?):String{
        return BASE_URL + API_KEY + "lookupteam.php?id=" + eventId
    }
}