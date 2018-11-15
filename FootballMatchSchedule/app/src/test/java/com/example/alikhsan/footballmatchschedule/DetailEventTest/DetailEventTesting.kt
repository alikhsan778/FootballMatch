package com.example.alikhsan.footballmatchschedule.DetailEventTest

import com.example.alikhsan.footballmatchschedule.TestContextProvider
import com.example.alikhsan.footballmatchschedule.model.DetailEvent
import com.example.alikhsan.footballmatchschedule.model.DetailEventResponse
import com.example.alikhsan.footballmatchschedule.model.Team
import com.example.alikhsan.footballmatchschedule.model.TeamHomeResponse
import com.example.alikhsan.footballmatchschedule.network.ApiRepo
import com.example.alikhsan.footballmatchschedule.network.SpotDBApi
import com.example.alikhsan.footballmatchschedule.presenter.TeamPresenter
import com.example.alikhsan.footballmatchschedule.presenter.TeamView
import com.google.gson.Gson

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailEventTesting {
    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepo

    @Mock
    private lateinit var teamsHome: List<Team>
    @Mock
    private lateinit var teamsAway: List<Team>

    @Mock
    private lateinit var detailEvent: List<DetailEvent>


    private lateinit var mPresent: TeamPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPresent = TeamPresenter(view, apiRepo, gson, TestContextProvider())

    }

    @Test
    fun getDetailEvent() {
        val lvTeamsAway : MutableList<Team> = mutableListOf()
        val responseAway = TeamHomeResponse(lvTeamsAway)
        val lvTeamHome : MutableList<Team> = mutableListOf()
        val responseTeam = TeamHomeResponse(lvTeamHome)
        val lvEvent : MutableList<DetailEvent> = mutableListOf()
        val responseEvent = DetailEventResponse (lvEvent)
        val mResponse = DetailEventResponse(detailEvent)
        val mITeamRespone = TeamHomeResponse(teamsHome)
        val mIAwayRespone = TeamHomeResponse(teamsAway)
        val teamHomeId = "133610"
        val teamAwayId = "133615"
        val eventId = "576588"
        var mError = apiRepo.doRequest(SpotDBApi.getDetailMatch(teamAwayId))


        `when`(gson.fromJson(apiRepo.doRequest(SpotDBApi.getDetailMatch(teamAwayId)),
            TeamHomeResponse::class.java)).thenReturn(responseAway)


        `when`(gson.fromJson(apiRepo.doRequest(SpotDBApi.getDetailMatch(teamHomeId)),
            TeamHomeResponse::class.java)).thenReturn(responseTeam)

        `when`(gson.fromJson(apiRepo.doRequest(SpotDBApi.getDetailMatchEvent(eventId)),
            DetailEventResponse::class.java)).thenReturn(responseEvent)



        mPresent.getDetailTeam(teamHomeId, teamAwayId, eventId)
        verify(view).showLoading()
        verify(view).loadGambarTeamHome(lvTeamHome)
//        verify(view).loadGambarTeamAway(teamsAway)
//        verify(view).datailEvent(detailEvent)
//        verify(view).hideLoading()

    }


}