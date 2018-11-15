package com.example.alikhsan.footballmatchschedule

import com.example.alikhsan.footballmatchschedule.others.ContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : ContextProvider() {
    override val main: CoroutineContext = Unconfined
}