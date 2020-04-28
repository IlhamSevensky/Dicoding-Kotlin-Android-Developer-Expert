package com.ilham.made.lastsubmission.ui.leaguedetail.content.leaguestanding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.adapter.StandingAdapter
import com.ilham.made.lastsubmission.data.repository.LeagueStandingState
import com.ilham.made.lastsubmission.ui.leaguedetail.DetailLeagueActivity
import kotlinx.android.synthetic.main.fragment_league_standings.*
import kotlinx.android.synthetic.main.progress_bar.*

/**
 * A simple [Fragment] subclass.
 */
class LeagueStandingsFragment : Fragment() {

    private val leagueStandingsViewModel: LeagueStandingsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(this@LeagueStandingsFragment, LeagueStandingsViewModel.Factory(activity.application))
            .get(LeagueStandingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_standings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        leagueStandingsViewModel.setLeagueId(DetailLeagueActivity.leagueId)
        leagueStandingsViewModel.getTeamStandings()?.observe(viewLifecycleOwner, Observer {
            rv_league_standings.adapter?.let { adapter ->
                when (adapter) {
                    is StandingAdapter -> {
                        adapter.setTeams(it)
                    }
                }
            }
        })
        leagueStandingsViewModel.leagueStandingsState.observer(viewLifecycleOwner, Observer {
            getUIState(it)
        })

    }

    private fun setupRecyclerView() {
        rv_league_standings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = StandingAdapter(mutableListOf())
        }
    }

    private fun getUIState(it: LeagueStandingState) {
        when (it) {
            is LeagueStandingState.IsLoading -> {
                isLoading(it.state)
            }
            is LeagueStandingState.Error -> {
                isLoading(false)
                Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
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
