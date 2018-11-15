package com.example.alikhsan.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alikhsan.footballmatchschedule.DetailActivity
import com.example.alikhsan.footballmatchschedule.R
import com.example.alikhsan.footballmatchschedule.model.EventTeam
import com.example.alikhsan.footballmatchschedule.others.Utility
import org.jetbrains.anko.startActivity


class EventListAdapter(val mContext: Context?, val dataSet: List<EventTeam>) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_content_shc_football, p0, false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val mDataSet: EventTeam = dataSet[p1]
        p0.mTeamHomeName.text = mDataSet.homeTeamName
        p0.mTeamAwayName.text = mDataSet.awayTeamName
        p0.mScoreTeamHome.text = mDataSet.homeTeamScore
        p0.mScoreTeamAway.text = mDataSet.awayTeamScore
        p0.mDate.text = Utility.getDateConvert(mDataSet.eventDate, 1)
        p0.itemView.setOnClickListener {
            mContext?.startActivity<DetailActivity>("data_club" to mDataSet,"op" to "Event")

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mDate = itemView.findViewById<TextView>(R.id.tv_date_match)
        val mTeamHomeName = itemView.findViewById<TextView>(R.id.tv_club_home)
        val mTeamAwayName = itemView.findViewById<TextView>(R.id.tv_club_away)
        val mScoreTeamHome = itemView.findViewById<TextView>(R.id.tv_score_home)
        val mScoreTeamAway = itemView.findViewById<TextView>(R.id.tv_score_away)
    }
}