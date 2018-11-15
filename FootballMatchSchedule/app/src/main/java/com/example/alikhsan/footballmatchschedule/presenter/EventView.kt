package com.example.alikhsan.footballmatchschedule.presenter

import com.example.alikhsan.footballmatchschedule.model.EventTeam


interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data:List<EventTeam>)
}