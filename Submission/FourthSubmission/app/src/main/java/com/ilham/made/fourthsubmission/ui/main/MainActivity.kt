package com.ilham.made.fourthsubmission.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.fourthsubmission.R
import com.ilham.made.fourthsubmission.adapter.LeagueAdapter
import com.ilham.made.fourthsubmission.data.repository.LeagueState
import com.ilham.made.fourthsubmission.model.LeagueModel
import com.ilham.made.fourthsubmission.ui.favoritematch.FavoriteMatchActivity
import com.ilham.made.fourthsubmission.ui.leaguedetail.DetailLeagueActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        val activity = requireNotNull(this@MainActivity) {
        }

        ViewModelProvider(this@MainActivity, MainViewModel.Factory(activity.application))
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupRecyclerView()
        mainViewModel.getListLeague()?.observe(this@MainActivity, Observer {
            rv_league.adapter?.let { adapter ->
                when (adapter) {
                    is LeagueAdapter -> {
                        Log.d("TES", "STARTED")
                        adapter.setLeagues(it)
                        adapter.setOnLeagueClickCallback(object :
                            LeagueAdapter.LeagueClickCallback {
                            override fun onItemClicked(data: LeagueModel) {
                                startActivity<DetailLeagueActivity>(DetailLeagueActivity.EXTRA_DATA to data.idLeague)
                            }
                        })
                    }
                }
            }
        })

        mainViewModel.state.observer(this@MainActivity, Observer {
            getUIState(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_match_action -> {
                startActivity<FavoriteMatchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        rv_league.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = LeagueAdapter(mutableListOf())
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.elevation = 8F
    }

    private fun getUIState(it: LeagueState) {
        when (it) {
            is LeagueState.IsLoading -> {
                isLoading(it.state)
            }
            is LeagueState.Error -> {
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
