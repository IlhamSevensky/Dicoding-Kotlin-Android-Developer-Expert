package com.ilham.made.lastsubmission.ui.favorite.content.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.adapter.FavoriteTeamAdapter
import com.ilham.made.lastsubmission.model.TeamFavoriteModel
import com.ilham.made.lastsubmission.ui.teamdetail.TeamDetailActivity
import com.ilham.made.lastsubmission.utils.Helper
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment() {

    private val favoriteTeamViewModel: FavoriteTeamViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(
            this@FavoriteTeamFragment,
            FavoriteTeamViewModel.Factory(activity.application)
        )
            .get(FavoriteTeamViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        favoriteTeamViewModel.favoriteTeam?.observe(viewLifecycleOwner, Observer {
            rv_favorite_team.adapter?.let { adapter ->
                when (adapter) {
                    is FavoriteTeamAdapter -> {
                        adapter.setTeams(it)
                        adapter.setOnTeamClickCallback(object :
                            FavoriteTeamAdapter.TeamClickCallback {
                            override fun onItemClicked(data: TeamFavoriteModel) {
                                val team = Helper.convertTeamFavoriteModelToTeamModel(data)
                                startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_DATA to team)
                            }
                        })
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rv_favorite_team.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FavoriteTeamAdapter(mutableListOf())
        }
    }
}
