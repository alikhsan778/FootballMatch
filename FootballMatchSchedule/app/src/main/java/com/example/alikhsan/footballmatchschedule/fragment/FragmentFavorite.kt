package com.example.alikhsan.footballmatchschedule.fragment


import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alikhsan.footballmatchschedule.R
import com.example.alikhsan.footballmatchschedule.adapter.FavListAdapter
import com.example.alikhsan.footballmatchschedule.db.FavDBHelper
import com.example.alikhsan.footballmatchschedule.db.fav_db
import com.example.alikhsan.footballmatchschedule.model.FavTeam
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FragmentFavorite : Fragment() {
    private lateinit var dataFavorite : MutableList<FavTeam>
    private lateinit var mFavListAdapter: FavListAdapter

    companion object {
        private val TAG : String = FragmentFavorite.javaClass.simpleName
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataFavorite = mutableListOf()
        dataFavorite.clear()
        mFavListAdapter = FavListAdapter(context,dataFavorite)
        rv_list_sch_football.layoutManager = LinearLayoutManager(context)
        rv_list_sch_football.adapter  = mFavListAdapter
        bindFavoriteData()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite,container,false)
    }

    private fun bindFavoriteData(){

        try {
            context?.fav_db?.use {
                val mResult = select(FavDBHelper.TABLE_FAV)
                val decRes = mResult.parseList(classParser<FavTeam>())
                dataFavorite.clear()
                dataFavorite.addAll(decRes)
                mFavListAdapter.notifyDataSetChanged()
            }
        }catch (e : SQLiteConstraintException){
            Log.e(TAG,"Error Bind : ${e.message}")
        }

    }

    override fun onResume() {
        super.onResume()
        bindFavoriteData()
    }
}