package com.example.alikhsan.footballmatchschedule.presenter

import com.example.alikhsan.footballmatchschedule.model.EventTeamResponse
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

class EventPresenter(
    private val view: EventView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val mContext:ContextProvider = ContextProvider()
) : AnkoLogger {

    fun getEventList(mId: String?, mEvent: String?) {
        view.showLoading()
        async(mContext.main){
            val dataList = bg {
                gson.fromJson(
                apiRepo.doRequest(SpotDBApi.getEventFootball(mId, mEvent)),
                EventTeamResponse::class.java)
            }
            view.showEventList(dataList.await().events)
            view.hideLoading()
        }

    }
}

