package com.example.alikhsan.footballmatchschedule.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alikhsan.footballmatchschedule.R
import com.example.alikhsan.footballmatchschedule.adapter.EventListAdapter
import com.example.alikhsan.footballmatchschedule.model.EventTeam
import com.example.alikhsan.footballmatchschedule.network.ApiRepo
import com.example.alikhsan.footballmatchschedule.others.Utility
import com.example.alikhsan.footballmatchschedule.presenter.EventPresenter
import com.example.alikhsan.footballmatchschedule.presenter.EventView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cus_tab_layout.*
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentMain : Fragment(), EventView {


    private lateinit var dataEvents: MutableList<EventTeam>
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mEventPresenter: EventPresenter
    private lateinit var mEventAdapter: EventListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataEvents = mutableListOf()
        dataEvents.clear()
        mProgressDialog = ProgressDialog(context)
        mEventAdapter = EventListAdapter(context, dataEvents)
        rv_list_sch_football.layoutManager = LinearLayoutManager(context)
        rv_list_sch_football.adapter = mEventAdapter
        val mApiRepo = ApiRepo()
        val mGson = Gson()
        mEventPresenter = EventPresenter(
            this,
            mApiRepo,
            mGson
        )
        inVisibility(eventLast)
        mEventPresenter.getEventList(leagueId, eventLast)
        tv_last_match_menu.setOnClickListener {
            inVisibility(eventLast)
            mEventPresenter.getEventList(leagueId, eventLast)
        }
        tv_next_match_menu.setOnClickListener {
            inVisibility(eventNext)
            mEventPresenter.getEventList(leagueId, eventNext)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun showLoading() {
        Utility.openProgressDialog(mProgressDialog, "Please Wait")

    }

    override fun hideLoading() {
        mProgressDialog.dismiss()
    }

    override fun showEventList(data: List<EventTeam>) {
        dataEvents.clear()
        dataEvents.addAll(data)
        mEventAdapter.notifyDataSetChanged()
    }

    companion object {
        const val leagueId = "4328"
        const val eventNext = "eventsnextleague.php"
        const val eventLast = "eventspastleague.php"
    }

    fun inVisibility(mEvent: String?) {
        when (mEvent) {
            eventLast -> {
                v_last_match.visibility = View.VISIBLE
                v_next_match.visibility = View.INVISIBLE
            }
            else -> {
                v_last_match.visibility = View.INVISIBLE
                v_next_match.visibility = View.VISIBLE
            }

        }

    }

}