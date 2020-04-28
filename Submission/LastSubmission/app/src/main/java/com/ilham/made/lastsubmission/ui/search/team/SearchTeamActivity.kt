package com.ilham.made.lastsubmission.ui.search.team

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.adapter.TeamAdapter
import com.ilham.made.lastsubmission.data.repository.SearchTeamState
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.ui.matchdetail.DetailMatchActivity
import com.ilham.made.lastsubmission.ui.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.activity_search_team.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.startActivity

class SearchTeamActivity : AppCompatActivity() {

    private val searchTeamViewModel: SearchTeamViewModel by lazy {
        val activity = requireNotNull(this@SearchTeamActivity) {
        }

        ViewModelProvider(this@SearchTeamActivity,
            SearchTeamViewModel.Factory(
                activity.application
            )
        )
            .get(SearchTeamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)
        setupRecyclerView()
        setupSearchView()

        searchTeamViewModel.getListSearchTeam()?.observe(this@SearchTeamActivity, Observer {
            rv_search_team.adapter?.let { adapter ->
                when (adapter) {
                    is TeamAdapter -> {
                        adapter.setTeams(it)
                        adapter.setOnTeamClickCallback(object : TeamAdapter.TeamClickCallback {
                            override fun onItemClicked(data: TeamModel) {
                                startActivity<DetailMatchActivity>(DetailMatchActivity.EXTRA_DATA to data)
                            }
                        })
                    }
                }
            }
        })

        searchTeamViewModel.searchTeamState.observer(this@SearchTeamActivity, Observer {
            getUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_search_team.apply {
            layoutManager = LinearLayoutManager(this@SearchTeamActivity)
            adapter = TeamAdapter(mutableListOf())
        }
    }

    private fun setupSearchView() {
        with(search_view_team) {
            setSearchHint(resources.getString(R.string.search_team))

            setOnHomeActionClickListener {
                finish()
            }

            setOnSearchListener(object : FloatingSearchView.OnSearchListener {
                override fun onSearchAction(currentQuery: String?) {
                    if (!currentQuery.isNullOrEmpty()) {
                        searchTeamViewModel.searchTeam(currentQuery)
                        searchTeamViewModel.getListSearchTeam()?.observe(this@SearchTeamActivity, Observer {
                            rv_search_team.adapter?.let { adapter ->
                                when (adapter) {
                                    is TeamAdapter -> {
                                        adapter.setTeams(it)
                                        adapter.setOnTeamClickCallback(object :
                                            TeamAdapter.TeamClickCallback {
                                            override fun onItemClicked(data: TeamModel) {
                                                startActivity<TeamDetailActivity>(
                                                    TeamDetailActivity.EXTRA_DATA to data)
                                            }
                                        })
                                    }
                                }
                            }
                        })
                    }
                }

                override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                    // Nothing
                }

            })
        }
    }

    private fun getUIState(it: SearchTeamState) {
        when (it) {
            is SearchTeamState.IsLoading -> {
                isLoading(it.state)
            }
            is SearchTeamState.Error -> {
                isLoading(false)
                Toast.makeText(this@SearchTeamActivity, it.error, Toast.LENGTH_LONG).show()
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
