package com.ilham.made.thirdsubmission.ui.leaguedetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.adapter.MatchSectionPageAdapter
import com.ilham.made.thirdsubmission.data.repository.DetailLeagueState
import com.ilham.made.thirdsubmission.ui.search.SearchActivity
import com.ilham.made.thirdsubmission.utils.Helper
import kotlinx.android.synthetic.main.activity_detail_league.*
import kotlinx.android.synthetic.main.content_detail_league.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.startActivity

class DetailLeagueActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        var leagueId: Int = 0
    }

    private val detailLeagueViewModel: DetailLeagueViewModel by lazy {
        val activity = requireNotNull(this@DetailLeagueActivity) {
        }

        ViewModelProvider(
            this@DetailLeagueActivity,
            DetailLeagueViewModel.Factory(activity.application)
        )
            .get(DetailLeagueViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        setupToolbar()
        setupTab()

        leagueId = intent.getIntExtra(EXTRA_DATA, 0)

        detailLeagueViewModel.setLeagueId(leagueId)
        detailLeagueViewModel.getDetailLeague()?.observe(this@DetailLeagueActivity, Observer {
            with(it[0]) {
                strBadge?.let { image ->
                    Helper.setImageToView(
                        this@DetailLeagueActivity,
                        image, img_detail_league
                    )
                }

                tv_detail_league_name.text = strLeague ?: resources.getString(R.string.empty_data)
                tv_detail_league_description.text =
                    strDescription ?: resources.getString(R.string.empty_data)

            }
        })

        detailLeagueViewModel.state.observer(this@DetailLeagueActivity, Observer {
            getUIState(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.league_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_match -> {
                startActivity<SearchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupTab() {
        val matchSectionPagerAdapter =
            MatchSectionPageAdapter(this@DetailLeagueActivity, supportFragmentManager)
        view_pager_detail_match.adapter = matchSectionPagerAdapter
        tab_layout_detail_match.setupWithViewPager(view_pager_detail_match)
    }

    private fun setupToolbar() {
        setSupportActionBar(detail_league_toolbar)
        supportActionBar?.elevation = 0F
        supportActionBar?.title = resources.getString(R.string.league_detail)
        detail_league_toolbar.navigationIcon =
            ContextCompat.getDrawable(this@DetailLeagueActivity, R.drawable.ic_arrow_back)
        detail_league_toolbar.setNavigationOnClickListener { finish() }
    }

    private fun getUIState(it: DetailLeagueState) {
        when (it) {
            is DetailLeagueState.IsLoading -> {
                isLoading(it.state)
            }
            is DetailLeagueState.Error -> {
                isLoading(false)
                Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isLoading(state: Boolean) {
        when (state) {
            true -> progress_bar.visibility = View.VISIBLE
            else -> progress_bar.visibility = View.GONE
        }
    }
}
