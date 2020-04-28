package com.ilham.made.secondsubmission.ui.match

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.utils.Helper
import com.ilham.made.secondsubmission.utils.Helper.replaceStringForFootballData
import com.ilham.made.secondsubmission.utils.Helper.replaceStringForPlayerData
import com.ilham.made.secondsubmission.utils.Helper.setImage
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.item_row_match.*

class MatchDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        setupToolbar()

        val data: MatchModel? = intent.getParcelableExtra(EXTRA_DATA)

        if (data != null) {
            with(data) {

                if (intRound != null) {
                    tv_match_round.text =
                        resources.getString(R.string.match_round, intRound.toString())
                } else {
                    tv_match_round.text = resources.getString(R.string.empty_data)
                }

                tv_match_home_team.text = strHomeTeam ?: resources.getString(R.string.empty_data)

                tv_match_away_team.text = strAwayTeam ?: resources.getString(R.string.empty_data)

                tv_match_schedule.text =
                    Helper.changeDateFormat(
                        dateEvent,
                        strTime
                    )

                if (strPostponed.equals("yes")) {
                    tv_detail_match_status.text = resources.getString(R.string.match_postponed)
                } else {
                    if (intHomeScore != null || intAwayScore != null) {
                        tv_detail_match_status.text = resources.getString(R.string.match_fulltime)
                    }
                }

                if (strBadgeHomeTeam != null) {
                    setImage(
                        this@MatchDetailActivity,
                        strBadgeHomeTeam,
                        img_match_home_team
                    )
                }

                if (strBadgeAwayTeam != null) {
                    setImage(
                        this@MatchDetailActivity,
                        strBadgeAwayTeam,
                        img_match_away_team
                    )
                }

                if (intHomeScore != null) {
                    tv_match_home_score.text = intHomeScore.toString()
                } else {
                    tv_match_home_score.text = resources.getString(R.string.empty_data)
                }

                if (intAwayScore != null) {
                    tv_match_away_score.text = intAwayScore.toString()
                } else {
                    tv_match_away_score.text = resources.getString(R.string.empty_data)
                }

                if (intAwayScore != null) {
                    tv_match_away_score.text = data.intAwayScore.toString()
                }

                if (strHomeFormation != null) {
                    if (strHomeFormation.isNotEmpty()) {
                        tv_detail_home_formation.text = strHomeFormation
                    }
                }


                if (strAwayFormation != null) {
                    if (strAwayFormation.isNotEmpty())
                        tv_detail_away_formation.text = strAwayFormation
                }

                if (strHomeGoalDetails != null) {
                    if (strHomeGoalDetails.isNotEmpty()) {
                        tv_detail_home_goal.text =
                            replaceStringForFootballData(
                                strHomeGoalDetails
                            )
                    }
                }

                if (strAwayGoalDetails != null) {
                    if (strAwayGoalDetails.isNotEmpty()) {
                        tv_detail_away_goal.text =
                            replaceStringForFootballData(
                                strAwayGoalDetails
                            )
                    }
                }

                if (intHomeShots != null) {
                    tv_detail_home_shot.text = intHomeShots.toString()
                }

                if (intAwayShots != null) {
                    tv_detail_away_shot.text = intAwayShots.toString()
                }

                if (strHomeRedCards != null) {
                    if (strHomeRedCards.isNotEmpty())
                        tv_detail_home_red_card.text =
                            replaceStringForFootballData(
                                strHomeRedCards
                            )
                }

                if (strAwayRedCards != null) {
                    if (strAwayRedCards.isNotEmpty()) {
                        tv_detail_away_red_card.text =
                            replaceStringForFootballData(
                                strAwayRedCards
                            )
                    }
                }

                if (strHomeYellowCards != null) {
                    if (strHomeYellowCards.isNotEmpty())
                        tv_detail_home_yellow_card.text =
                            replaceStringForFootballData(
                                strHomeYellowCards
                            )
                }


                if (strAwayYellowCards != null) {
                    if (strAwayYellowCards.isNotEmpty()) {
                        tv_detail_away_yellow_card.text =
                            replaceStringForFootballData(
                                strAwayYellowCards
                            )
                    }
                }


                if (strHomeLineupGoalkeeper != null) {
                    if (strHomeLineupGoalkeeper.isNotEmpty()) {
                        tv_detail_home_lineup_goalkeeper.text =
                            replaceStringForPlayerData(
                                strHomeLineupGoalkeeper
                            )
                    }
                }


                if (strAwayLineupGoalkeeper != null) {
                    if (strAwayLineupGoalkeeper.isNotEmpty()) {
                        tv_detail_away_lineup_goalkeeper.text =
                            replaceStringForPlayerData(
                                strAwayLineupGoalkeeper
                            )
                    }
                }



                if (strHomeLineupDefense != null) {
                    if (strHomeLineupDefense.isNotEmpty()) {
                        tv_detail_home_lineup_defender.text =
                            replaceStringForPlayerData(
                                strHomeLineupDefense
                            )
                    }
                }

                if (strAwayLineupDefense != null) {
                    if (strAwayLineupDefense.isNotEmpty()) {
                        tv_detail_away_lineup_defender.text =
                            replaceStringForPlayerData(
                                strHomeLineupDefense
                            )
                    }
                }



                if (strHomeLineupMidfield != null) {
                    if (strHomeLineupMidfield.isNotEmpty()) {
                        tv_detail_home_lineup_midfielder.text =
                            replaceStringForPlayerData(
                                strHomeLineupMidfield
                            )
                    }
                }


                if (strAwayLineupMidfield != null) {
                    if (strAwayLineupMidfield.isNotEmpty()) {
                        tv_detail_away_lineup_midfielder.text =
                            replaceStringForPlayerData(
                                strAwayLineupMidfield
                            )
                    }
                }

                if (strHomeLineupForward != null) {
                    if (strHomeLineupForward.isNotEmpty()) {
                        tv_detail_home_lineup_striker.text =
                            replaceStringForPlayerData(
                                strHomeLineupForward
                            )
                    }
                }


                if (strAwayLineupForward != null) {
                    if (strAwayLineupForward.isNotEmpty()) {
                        tv_detail_away_lineup_striker.text =
                            replaceStringForPlayerData(
                                strAwayLineupForward
                            )
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
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_24dp)
        detail_match_toolbar.setNavigationOnClickListener { finish() }
    }
}
