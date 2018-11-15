package com.example.alikhsan.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavDBHelper (mContext: Context) : ManagedSQLiteOpenHelper(mContext, "TeamFav.db", null, 1) {

    companion object {
        const val TABLE_FAV: String = "tbl_fav"
        const val _ID: String = "_id"
        const val EVENT_ID: String = "id_event"
        const val EVENT_DATE: String = "date_event"
        const val TEAM_HOME_ID: String = "team_home_id"
        const val TEAM_AWAY_ID: String = "team_away_id"
        const val TEAM_HOME_NAME: String = "team_home_name"
        const val TEAM_AWAY_NAME: String = "team_away_name"
        const val SCORE_TEAM_HOME: String = "score_team_home"
        const val SCORE_TEAM_AWAY: String = "score_team_away"
        const val BADGE_TEAM_HOME: String = "badge_team_home"
        const val BADGE_TEAM_AWAY: String = "badge_team_away"

        private var instance: FavDBHelper? = null

        @Synchronized
        fun getInstance(mContext: Context): FavDBHelper {
            if (instance == null) {
                instance = FavDBHelper(mContext)
            }
            return instance as FavDBHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            TABLE_FAV, true,
            _ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EVENT_ID to TEXT,
            EVENT_DATE to TEXT,
            TEAM_HOME_ID to TEXT,
            TEAM_AWAY_ID to TEXT,
            TEAM_HOME_NAME to TEXT,
            TEAM_AWAY_NAME to TEXT,
            SCORE_TEAM_HOME to TEXT,
            SCORE_TEAM_AWAY to TEXT,
            BADGE_TEAM_HOME to TEXT,
            BADGE_TEAM_AWAY to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TABLE_FAV,true)
    }



}
val Context.fav_db : FavDBHelper
    get() = FavDBHelper.getInstance(applicationContext)