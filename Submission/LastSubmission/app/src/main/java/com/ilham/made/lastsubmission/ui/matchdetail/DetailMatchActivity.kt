package com.ilham.made.lastsubmission.ui.matchdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.data.db.dao.getMatchFavoriteDatabase
import com.ilham.made.lastsubmission.data.repository.MatchFavoriteRepository
import com.ilham.made.lastsubmission.model.MatchModel
import com.ilham.made.lastsubmission.utils.Helper.parseDateTimeFormat
import com.ilham.made.lastsubmission.utils.Helper.replaceStringForFootballData
import com.ilham.made.lastsubmission.utils.Helper.replaceStringForPlayerData
import com.ilham.made.lastsubmission.utils.Helper.setImageToView
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMatchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        setupToolbar()

        val favoriteMatchFavoriteRepository =
            MatchFavoriteRepository(
                getMatchFavoriteDatabase(this@DetailMatchActivity)
            )

        val data: MatchModel? = intent.getParcelableExtra(EXTRA_DATA)

        if (data != null) {
            with(data) {

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        if (favoriteMatchFavoriteRepository.validateMatch(idEvent) > 0) {
                            withContext(Dispatchers.Main) {
                                btn_favorite.setImageResource(0)
                                btn_favorite.setImageResource(R.drawable.ic_favorite_true)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                btn_favorite.setImageResource(0)
                                btn_favorite.setImageResource(R.drawable.ic_favorite_false)
                            }
                        }
                    }
                }

                if (!intRound?.toString().isNullOrEmpty()) {
                    tv_match_round.text =
                        resources.getString(R.string.match_round, intRound.toString())
                } else {
                    tv_match_round.text = resources.getString(R.string.empty_data)
                }

                tv_match_home_team.text = strHomeTeam ?: resources.getString(R.string.empty_data)

                tv_match_away_team.text = strAwayTeam ?: resources.getString(R.string.empty_data)

                tv_match_schedule.text =
                    parseDateTimeFormat(
                        dateEvent,
                        strTime
                    )

                if (strPostponed.equals("yes", ignoreCase = true)) {
                    tv_detail_match_status.text = resources.getString(R.string.match_postponed)
                } else {
                    if (intHomeScore?.toString().isNullOrEmpty() || intAwayScore?.toString()
                            .isNullOrEmpty()
                    ) {
                        tv_detail_match_status.text = resources.getString(R.string.match_fulltime)
                    }
                }

                if (!strBadgeHomeTeam.isNullOrEmpty()) {
                    setImageToView(
                        this@DetailMatchActivity,
                        strBadgeHomeTeam!!,
                        img_match_home_team
                    )
                }

                if (!strBadgeAwayTeam.isNullOrEmpty()) {
                    setImageToView(
                        this@DetailMatchActivity,
                        strBadgeAwayTeam!!,
                        img_match_away_team
                    )
                }

                tv_match_home_score.text =
                    intHomeScore?.toString() ?: resources.getString(R.string.empty_data)

                tv_match_away_score.text =
                    intAwayScore?.toString() ?: resources.getString(R.string.empty_data)

                if (strHomeFormation != null) {
                    if (strHomeFormation.isNotEmpty()) {
                        tv_detail_home_formation.text = strHomeFormation
                    }
                }


                if (!strAwayFormation.isNullOrEmpty()) {
                    tv_detail_away_formation.text = strAwayFormation
                }

                if (!strHomeGoalDetails.isNullOrEmpty()) {
                    tv_detail_home_goal.text =
                        replaceStringForFootballData(
                            strHomeGoalDetails
                        )

                }

                if (!strAwayGoalDetails.isNullOrEmpty()) {
                    tv_detail_away_goal.text =
                        replaceStringForFootballData(
                            strAwayGoalDetails
                        )
                }

                tv_detail_home_shot.text =
                    intHomeShots?.toString() ?: resources.getString(R.string.empty_data)

                tv_detail_away_shot.text =
                    intAwayShots?.toString() ?: resources.getString(R.string.empty_data)

                if (!strHomeRedCards.isNullOrEmpty()) {
                    tv_detail_home_red_card.text =
                        replaceStringForFootballData(
                            strHomeRedCards
                        )
                }

                if (!strAwayRedCards.isNullOrEmpty()) {
                    tv_detail_away_red_card.text =
                        replaceStringForFootballData(
                            strAwayRedCards
                        )
                }

                if (strHomeYellowCards.isNullOrEmpty()) {
                    tv_detail_home_yellow_card.text =
                        replaceStringForFootballData(
                            strHomeYellowCards
                        )
                }


                if (!strAwayYellowCards.isNullOrEmpty()) {
                    tv_detail_away_yellow_card.text =
                        replaceStringForFootballData(
                            strAwayYellowCards
                        )
                }


                if (!strHomeLineupGoalkeeper.isNullOrEmpty()) {
                    tv_detail_home_lineup_goalkeeper.text =
                        replaceStringForPlayerData(
                            strHomeLineupGoalkeeper
                        )
                }


                if (!strAwayLineupGoalkeeper.isNullOrEmpty()) {
                    tv_detail_away_lineup_goalkeeper.text =
                        replaceStringForPlayerData(
                            strAwayLineupGoalkeeper
                        )
                }



                if (!strHomeLineupDefense.isNullOrEmpty()) {
                    tv_detail_home_lineup_defender.text =
                        replaceStringForPlayerData(
                            strHomeLineupDefense
                        )
                }

                if (!strAwayLineupDefense.isNullOrEmpty()) {
                    tv_detail_away_lineup_defender.text =
                        replaceStringForPlayerData(
                            strHomeLineupDefense
                        )
                }



                if (!strHomeLineupMidfield.isNullOrEmpty()) {
                    tv_detail_home_lineup_midfielder.text =
                        replaceStringForPlayerData(
                            strHomeLineupMidfield
                        )
                }


                if (!strAwayLineupMidfield.isNullOrEmpty()) {
                    tv_detail_away_lineup_midfielder.text =
                        replaceStringForPlayerData(
                            strAwayLineupMidfield
                        )
                }

                if (!strHomeLineupForward.isNullOrEmpty()) {
                    tv_detail_home_lineup_striker.text =
                        replaceStringForPlayerData(
                            strHomeLineupForward
                        )
                }


                if (!strAwayLineupForward.isNullOrEmpty()) {
                    tv_detail_away_lineup_striker.text =
                        replaceStringForPlayerData(
                            strAwayLineupForward
                        )
                }

                btn_favorite.setOnClickListener {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO) {
                            if (favoriteMatchFavoriteRepository.validateMatch(idEvent) > 0) {
                                favoriteMatchFavoriteRepository.removeMatchFavorite(idEvent)
                                withContext(Dispatchers.Main) {
                                    btn_favorite.setImageResource(0)
                                    btn_favorite.setImageResource(R.drawable.ic_favorite_false)
                                }
                            } else {
                                favoriteMatchFavoriteRepository.addToFavoriteMatch(data)
                                withContext(Dispatchers.Main) {
                                    btn_favorite.setImageResource(0)
                                    btn_favorite.setImageResource(R.drawable.ic_favorite_true)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(detail_match_toolbar)
        supportActionBar?.title = resources.getString(R.string.match_detail)
        supportActionBar?.elevation = 8F
        detail_match_toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        detail_match_toolbar.setNavigationOnClickListener { finish() }
    }
}
