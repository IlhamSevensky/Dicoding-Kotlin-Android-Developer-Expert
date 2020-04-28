package com.ilham.made.secondsubmission.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.adapters.LeagueAdapter
import com.ilham.made.secondsubmission.models.LeagueModel
import com.ilham.made.secondsubmission.ui.detail.DetailLeagueActivity
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

        mainViewModel.getListLeagues()?.observe(this@MainActivity, Observer {
            rv_league.adapter?.let { adapter ->
                when (adapter) {
                    is LeagueAdapter -> {
                        adapter.setLeagues(it)
                        adapter.setOnItemClickCallback(object : LeagueAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: LeagueModel) {
                                startActivity<DetailLeagueActivity>(DetailLeagueActivity.EXTRA_DATA to data.idLeague)
                            }

                        })
                    }
                }
            }
        })

        mainViewModel.getLeagueState().observer(this@MainActivity, Observer {
            handleUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_league.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = LeagueAdapter(mutableListOf())
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.elevation = 8F
        main_toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun handleUIState(it: LeagueState) {
        when (it) {
            is LeagueState.IsLoading -> {
                isLoading(it.state)
            }
            is LeagueState.Error -> {
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
