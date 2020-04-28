package com.ilham.made.secondsubmission.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.adapters.MatchSectionPageAdapter
import com.ilham.made.secondsubmission.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_detail_league.*
import kotlinx.android.synthetic.main.content_detail_match.*
import kotlinx.android.synthetic.main.content_league_detail.*
import kotlinx.android.synthetic.main.progress_bar.*


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
        setupTabLayout()
        leagueId = intent.getIntExtra(EXTRA_DATA, 0)

        detailLeagueViewModel.getDetailLeagueFromApi(leagueId)
        detailLeagueViewModel.getDetailLeague().observe(this@DetailLeagueActivity, Observer {
            with(it[0]) {
                detail_league_name.text = strLeague
                detail_desc_league.text = strDescription
                Glide.with(this@DetailLeagueActivity).load(strBadge).into(detail_img_league)
                collapsing_toolbar.title = strLeague
            }
        })

        detailLeagueViewModel.getDetailLeagueState().observer(this@DetailLeagueActivity, Observer {
            handleUIState(it)
        })
    }

    private fun setupTabLayout() {
        val sectionPagerAdapter = MatchSectionPageAdapter(this, supportFragmentManager)
        view_pager_match.adapter = sectionPagerAdapter
        tab_match.setupWithViewPager(view_pager_match)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.elevation = 0F
        toolbar.elevation = 0F
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))
        toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener { finish() }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.league_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_match -> {
                startActivity(Intent(this@DetailLeagueActivity, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleUIState(it: DetailLeagueState) {
        when (it) {
            is DetailLeagueState.IsLoading -> {
                isLoading(it.state)
            }
            is DetailLeagueState.Error -> {
                isLoading(false)
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }
}
