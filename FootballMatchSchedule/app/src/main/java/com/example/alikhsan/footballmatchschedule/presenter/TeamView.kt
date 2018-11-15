package com.example.alikhsan.footballmatchschedule.presenter

import com.example.alikhsan.footballmatchschedule.model.DetailEvent
import com.example.alikhsan.footballmatchschedule.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun loadGambarTeamHome(gambar:List<Team>)
    fun loadGambarTeamAway (gambar:List<Team>)
    fun datailEvent(dataDetailEvent : List<DetailEvent>)
}