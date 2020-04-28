package com.ilham.made.lastsubmission.ui.leaguedetail.content.team

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
import com.ilham.made.lastsubmission.adapter.TeamAdapter
import com.ilham.made.lastsubmission.data.repository.TeamState
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.ui.leaguedetail.DetailLeagueActivity
import com.ilham.made.lastsubmission.ui.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment() {

    private val teamViewModel: TeamViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(this@TeamFragment, TeamViewModel.Factory(activity.application))
            .get(TeamViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        teamViewModel.setLeagueId(DetailLeagueActivity.leagueId)
        teamViewModel.getListTeam()?.observe(viewLifecycleOwner, Observer {
            rv_team.adapter.let {adapter ->
                when (adapter){
                    is TeamAdapter -> {
                        adapter.setTeams(it)
                        adapter.setOnTeamClickCallback(object : TeamAdapter.TeamClickCallback{
                            override fun onItemClicked(data: TeamModel) {
                                startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_DATA to data)
                            }
                        })
                    }
                }
            }
        })

        teamViewModel.teamState.observer(viewLifecycleOwner, Observer {
            getUIState(it)
        })

    }

    private fun setupRecyclerView() {
        rv_team.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TeamAdapter(mutableListOf())
        }
    }

    private fun getUIState(it: TeamState) {
        when (it) {
            is TeamState.IsLoading -> {
                isLoading(it.state)
            }
            is TeamState.Error -> {
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
