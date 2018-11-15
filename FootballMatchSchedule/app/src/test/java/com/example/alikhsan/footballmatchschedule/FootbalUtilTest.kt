package com.example.alikhsan.footballmatchschedule

import android.net.Uri
import com.example.alikhsan.footballmatchschedule.network.ApiRepo
import com.example.alikhsan.footballmatchschedule.network.SpotDBApi
import com.example.alikhsan.footballmatchschedule.network.SpotDBApi.API_KEY
import com.example.alikhsan.footballmatchschedule.network.SpotDBApi.BASE_URL
import com.example.alikhsan.footballmatchschedule.others.Utility
import com.example.alikhsan.footballmatchschedule.presenter.TeamPresenter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class FootbalUtilTest {
    @Test
    fun testDateFormaterCustom(){
        val dateResultTranslator = Utility.getDateConvert("2018-11-11",1)
        assertEquals("Sunday, 11 Nov 2018",dateResultTranslator)
    }

    @Test
    fun testGetDataPastMatch(){
        val apiRepo = mock(ApiRepo::class.java)
        val pUri = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepo.doRequest(pUri)
        verify(apiRepo).doRequest(pUri)
    }

    @Test
    fun testGetDataNextMatch(){
        val apiRepo = mock(ApiRepo::class.java)
        val pUri = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepo.doRequest(pUri)
        verify(apiRepo).doRequest(pUri)
    }





}