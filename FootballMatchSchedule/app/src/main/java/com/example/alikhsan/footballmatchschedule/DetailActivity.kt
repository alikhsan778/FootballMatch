package com.example.alikhsan.footballmatchschedule

import android.app.ProgressDialog
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.alikhsan.footballmatchschedule.R.drawable.*
import com.example.alikhsan.footballmatchschedule.R.id.make_favorite
import com.example.alikhsan.footballmatchschedule.R.menu.toolbar_menu
import com.example.alikhsan.footballmatchschedule.db.FavDBHelper
import com.example.alikhsan.footballmatchschedule.db.fav_db
import com.example.alikhsan.footballmatchschedule.model.DetailEvent
import com.example.alikhsan.footballmatchschedule.model.EventTeam
import com.example.alikhsan.footballmatchschedule.model.FavTeam
import com.example.alikhsan.footballmatchschedule.model.Team
import com.example.alikhsan.footballmatchschedule.network.ApiRepo
import com.example.alikhsan.footballmatchschedule.others.Utility
import com.example.alikhsan.footballmatchschedule.presenter.TeamPresenter
import com.example.alikhsan.footballmatchschedule.presenter.TeamView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailActivity : AppCompatActivity(), TeamView {


    private lateinit var dataEventTeam: EventTeam
    private lateinit var dataFavoriteTeam: FavTeam
    private lateinit var mTeamPresenter: TeamPresenter
    private lateinit var mProgressDialog: ProgressDialog


    private var nMenu: Menu? = null
    private var checkFav: Boolean = false

    private var nBadgeTeamHome: String? = null
    private var nBadgeTeamAway: String? = null
    private var nHomeTeamScore: String? = null
    private var nAwayTeamScore: String? = null
    private var nHomeTeamName: String? = null
    private var nAwayTeamName: String? = null
    private var nHomeTeamId: String? = null
    private var nAwayTeamId: String? = null
    private var nEventDate: String? = null
    private var nEventId: String? = null
    private var mOption: String? = null

    companion object {
        private var TAG = DetailActivity.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        mOption = intent.getStringExtra("op")
        if (mOption.equals("Favorite")) {
            dataFavoriteTeam = intent.getParcelableExtra("data_club")
            nHomeTeamScore = dataFavoriteTeam.mScoreTeamHome
            nAwayTeamScore = dataFavoriteTeam.mScoreTeamAway
            nHomeTeamName = dataFavoriteTeam.mTeamHomeName
            nAwayTeamName = dataFavoriteTeam.mTeamAwayName
            nEventDate = dataFavoriteTeam.mDateEvent
            nEventId = dataFavoriteTeam.mIdEvent
            nBadgeTeamHome = dataFavoriteTeam.mImageTeamHome
            nBadgeTeamAway = dataFavoriteTeam.mImageTeamAway
            nHomeTeamId = dataFavoriteTeam.mIdTeamHome
            nAwayTeamId = dataFavoriteTeam.mIdTeamAway
        } else {
            dataEventTeam = intent.getParcelableExtra("data_club")
            nHomeTeamScore = dataEventTeam.homeTeamScore
            nAwayTeamScore = dataEventTeam.awayTeamScore
            nHomeTeamName = dataEventTeam.homeTeamName
            nAwayTeamName = dataEventTeam.awayTeamName
            nEventDate = dataEventTeam.eventDate
            nEventId = dataEventTeam.eventId
            nHomeTeamId = dataEventTeam.homeTeamId
            nAwayTeamId = dataEventTeam.awayTeamId
        }

        mProgressDialog = ProgressDialog(this)

        tv_date_match.text = Utility.getDateConvert(nEventDate, 1)
        tv_score_home.text = nHomeTeamScore
        tv_score_away.text = nAwayTeamScore
        tv_club_home.text = nHomeTeamName
        tv_club_away.text = nAwayTeamName

//
        checkFav = readFavState(nEventId)
        val mApiRepo = ApiRepo()
        val mGson = Gson()
        mTeamPresenter = TeamPresenter(this, mApiRepo, mGson)
//        mTeamPresenter.getGambarList(nHomeTeamId, nAwayTeamId)
//
//        mTeamPresenter.getEventDetail(nEventId)
        mTeamPresenter.getDetailTeam(nHomeTeamId, nAwayTeamId,nEventId)


    }

    //listener menu when selected
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            make_favorite -> {
                if (checkFav) {
                    checkFav = false
                    changeStateFav(checkFav)
                    nEventId?.let { makeUnfavortieListener(it) }
                } else {
                    checkFav = true
                    changeStateFav(checkFav)
                    makeFavoriteListener()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadGambarTeamHome(gambar: List<Team>) {
        val mGambar: Team = gambar[0]
        mGambar.strTeamBadge.let {
            Glide.with(this)
                .load(it)
                .error(R.drawable.ic_image_broken)
                .placeholder(R.drawable.ic_image)
                .into(iv_home_pict_club)
            nBadgeTeamHome = it
        }

    }

    override fun loadGambarTeamAway(gambar: List<Team>) {
        val mGambar: Team = gambar[0]
        mGambar.strTeamBadge.let {
            Glide.with(this)
                .load(it)
                .error(R.drawable.ic_image_broken)
                .placeholder(R.drawable.ic_image)
                .into(iv_away_pict_club)
            nBadgeTeamAway = it
        }
    }

    override fun showLoading() {
        Utility.openProgressDialog(mProgressDialog, "Please Wait")
    }

    override fun hideLoading() {
        mProgressDialog.dismiss()
    }

    override fun datailEvent(dataDetailEvent: List<DetailEvent>) {
        val mEventDetailList: DetailEvent = dataDetailEvent[0]
        val homeGoals = mEventDetailList.homeGoalDetail?.split(";".toRegex())
        val awayGoals = mEventDetailList.awayGoalDetail?.split(";".toRegex())
        val homeGoalKeeper = mEventDetailList.homeGoalKeeper?.split(";".toRegex())
        val awayGoalKeeper = mEventDetailList.awayGoalKeeper?.split(";".toRegex())
        val homeDefense = mEventDetailList.homeDefense?.split(";".toRegex())
        val awayDefense = mEventDetailList.awayDefense?.split(";".toRegex())
        val homeMidfield = mEventDetailList.homeMidField?.split(";".toRegex())
        val awayMidfield = mEventDetailList.awayMidField?.split(";".toRegex())
        val homeForward = mEventDetailList.homeForward?.split(";".toRegex())
        val awayForward = mEventDetailList.awayForward?.split(";".toRegex())
        val homeSubtitutes = mEventDetailList.homeSubtitutes?.split(";".toRegex())
        val awaySubtitutes = mEventDetailList.awaySubtitutes?.split(";".toRegex())

        var sHomeGoals = ""
        var sawayGoals = ""
        var shomeGoalKeeper = ""
        var sawayGoalKeeper = ""
        var shomeDefense = ""
        var sawayDefense = ""
        var shomeMidfield = ""
        var sawayMidfield = ""
        var shomeForward = ""
        var sawayForward = ""
        var shomeSubtitutes = ""
        var sawaySubtitutes = ""

        mEventDetailList.homeShots?.let {
            tv_home_shots.text = it
        }
        mEventDetailList.awayShots?.let {
            tv_away_shots.text = it
        }

        homeGoals?.forEach { e -> sHomeGoals = sHomeGoals.trim() + "$e \n" }
        awayGoals?.forEach { e -> sawayGoals = sawayGoals.trim() + "$e \n" }
        homeGoalKeeper?.forEach { e -> shomeGoalKeeper = shomeGoalKeeper.trim() + "$e \n" }
        awayGoalKeeper?.forEach { e -> sawayGoalKeeper = sawayGoalKeeper.trim() + "$e \n" }
        homeDefense?.forEach { e -> shomeDefense = shomeDefense.trim() + "$e \n" }
        awayDefense?.forEach { e -> sawayDefense = sawayDefense.trim() + "$e \n" }
        homeMidfield?.forEach { e -> shomeMidfield = shomeMidfield.trim() + "$e \n" }
        awayMidfield?.forEach { e -> sawayMidfield = sawayMidfield.trim() + "$e \n" }
        homeForward?.forEach { e -> shomeForward = shomeForward.trim() + "$e \n" }
        awayForward?.forEach { e -> sawayForward = sawayForward.trim() + "$e \n" }
        homeSubtitutes?.forEach { e -> shomeSubtitutes = shomeSubtitutes.trim() + "$e \n" }
        awaySubtitutes?.forEach { e -> sawaySubtitutes = sawaySubtitutes.trim() + "$e \n" }

        tv_home_goals.text = sHomeGoals
        tv_away_goals.text = sawayGoals
        tv_home_goal_keeper.text = shomeGoalKeeper
        tv_away_goal_keeper.text = sawayGoalKeeper
        tv_home_defense.text = shomeDefense
        tv_away_defense.text = sawayDefense
        tv_home_midefield.text = shomeMidfield
        tv_away_midfield.text = sawayMidfield
        tv_home_forward.text = shomeForward
        tv_away_forward.text = sawayForward
        tv_home_subtitusi.text = shomeSubtitutes
        tv_away_subtitusi.text = sawaySubtitutes

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(toolbar_menu, menu)
        nMenu = menu
        changeStateFav(checkFav)
        return super.onCreateOptionsMenu(menu)


    }

    private fun changeStateFav(mState: Boolean) {
        if (mState) {
            nMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_makefav)
        } else {
            nMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_unfav)
        }

    }

    private fun makeFavoriteListener() {
        try {
            fav_db.use {
                insert(
                    FavDBHelper.TABLE_FAV,
                    FavDBHelper.EVENT_ID to nEventId,
                    FavDBHelper.EVENT_DATE to nEventDate,
                    FavDBHelper.TEAM_HOME_ID to nHomeTeamId,
                    FavDBHelper.TEAM_AWAY_ID to nAwayTeamId,
                    FavDBHelper.TEAM_HOME_NAME to nHomeTeamName,
                    FavDBHelper.TEAM_AWAY_NAME to nAwayTeamName,
                    FavDBHelper.SCORE_TEAM_HOME to nHomeTeamScore,
                    FavDBHelper.SCORE_TEAM_AWAY to nAwayTeamScore,
                    FavDBHelper.BADGE_TEAM_HOME to nBadgeTeamHome,
                    FavDBHelper.BADGE_TEAM_AWAY to nBadgeTeamAway
                )
            }
            snackbar(rl_detail_event_team, "Success Add On Favorite Team").show()
        } catch (e: SQLiteConstraintException) {
            Log.d(TAG, "Error Insert : ${e.message}")
            Toast.makeText(this, "Error : " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeUnfavortieListener(mId: String) {
        try {

            fav_db.use {
                delete(
                    FavDBHelper.TABLE_FAV, "(${FavDBHelper.EVENT_ID} = {id})",
                    "id" to mId
                )
            }
            snackbar(rl_detail_event_team, "Success Unfavorite Team").show()
        } catch (e: SQLiteConstraintException) {
            Log.d(TAG, "Error Delete : ${e.message}")
            Toast.makeText(this, "Error : " + e.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun readFavState(mId: String?): Boolean {
        var mState = false
        try {
            fav_db.use {
                val mResult = select(FavDBHelper.TABLE_FAV).whereArgs(
                    "(${FavDBHelper.EVENT_ID} = {id})",
                    "id" to mId!!
                )
                val decFav = mResult.parseList(classParser<FavTeam>())
                if (!decFav.isEmpty()) mState = true
            }
        } catch (e: SQLiteConstraintException) {
            Log.d(TAG, "Error Read : ${e.message}")
        }
        return mState

    }
}




