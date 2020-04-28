package com.ilham.made.lastsubmission.ui.teamdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.data.db.dao.getTeamFavoriteDatabase
import com.ilham.made.lastsubmission.data.repository.TeamFavoriteRepository
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.utils.Helper.clearImageView
import com.ilham.made.lastsubmission.utils.Helper.setImageToView
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setupToolbar()

        val favoriteTeamFavoriteRepository =
            TeamFavoriteRepository(
                getTeamFavoriteDatabase(this@TeamDetailActivity)
            )

        val data: TeamModel? = intent.getParcelableExtra(EXTRA_DATA)

        if (data != null) {
            with(data) {

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        if (favoriteTeamFavoriteRepository.validateTeam(idTeam) > 0) {
                            withContext(Dispatchers.Main) {
                                btn_team_favorite.setImageResource(0)
                                btn_team_favorite.setImageResource(R.drawable.ic_favorite_true)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                btn_team_favorite.setImageResource(0)
                                btn_team_favorite.setImageResource(R.drawable.ic_favorite_false)
                            }
                        }
                    }
                }

                img_detailteam.setImageResource(0)
                if (!strTeamBadge.isNullOrEmpty()) {
                    clearImageView(this@TeamDetailActivity, img_detailteam)
                    setImageToView(this@TeamDetailActivity, strTeamBadge, img_detailteam)
                } else {
                    img_detailteam.setImageResource(R.drawable.no_image_available)
                }

                tv_detailteam_name.text = strTeam ?: resources.getString(R.string.empty_data)
                tv_detailteam_desc.text =
                    strDescriptionEN ?: resources.getString(R.string.empty_data)
                tv_detailteam_stadium.text = resources.getString(
                    R.string.detail_team_stadium,
                    strStadium
                )

                btn_team_favorite.setOnClickListener {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO) {
                            if (favoriteTeamFavoriteRepository.validateTeam(idTeam) > 0) {
                                favoriteTeamFavoriteRepository.removeTeamFavorite(idTeam)
                                withContext(Dispatchers.Main) {
                                    btn_team_favorite.setImageResource(0)
                                    btn_team_favorite.setImageResource(R.drawable.ic_favorite_false)
                                }
                            } else {
                                favoriteTeamFavoriteRepository.addToFavoriteTeam(data)
                                withContext(Dispatchers.Main) {
                                    btn_team_favorite.setImageResource(0)
                                    btn_team_favorite.setImageResource(R.drawable.ic_favorite_true)
                                }
                            }
                        }
                    }
                }
            }


        }

    }

    private fun setupToolbar() {
        setSupportActionBar(detail_team_toolbar)
        supportActionBar?.title = resources.getString(R.string.team_detail)
        supportActionBar?.elevation = 8F
        detail_team_toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        detail_team_toolbar.setNavigationOnClickListener { finish() }
    }
}
