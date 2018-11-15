package com.example.alikhsan.footballmatchschedule.presenter

import com.example.alikhsan.footballmatchschedule.model.DetailEventResponse
import com.example.alikhsan.footballmatchschedule.model.TeamHomeResponse
import com.example.alikhsan.footballmatchschedule.network.ApiRepo
import com.example.alikhsan.footballmatchschedule.network.SpotDBApi
import com.example.alikhsan.footballmatchschedule.others.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(
    private val teamView: TeamView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val mContext: ContextProvider = ContextProvider()
) : AnkoLogger {

    fun getDetailTeam(mTeamHomeId: String?, mTeamAwayId: String?, mEventId: String?) {
        teamView.showLoading()
        async(mContext.main) {
            val dataHomeGambarList = bg {
                gson.fromJson(
                    apiRepo.doRequest(SpotDBApi.getGambarBadges(mTeamHomeId)), TeamHomeResponse::class.java
                )
            }
            val dataAwayGambarList = bg {
                gson.fromJson(
                    apiRepo.doRequest(SpotDBApi.getGambarBadges(mTeamAwayId)), TeamHomeResponse::class.java
                )
            }
            val dataEventList = bg {
                gson.fromJson(
                    apiRepo.doRequest(SpotDBApi.getDetailEvent(mEventId)), DetailEventResponse::class.java
                )
            }
            teamView.loadGambarTeamHome(dataHomeGambarList.await().teams)
            teamView.loadGambarTeamAway(dataAwayGambarList.await().teams)
            teamView.datailEvent(dataEventList.await().events)
            teamView.hideLoading()
        }
    }

}