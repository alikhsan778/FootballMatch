package com.example.alikhsan.footballmatchschedule.network

import java.net.URL

class ApiRepo {
    fun doRequest(mUrl : String) :String{
        return URL(mUrl).readText()
    }
}