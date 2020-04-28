package com.ilham.made.thirdsubmission.ui.leaguedetail.content.nextmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.adapter.MatchAdapter
import com.ilham.made.thirdsubmission.data.repository.NextMatchState
import com.ilham.made.thirdsubmission.model.MatchModel
import com.ilham.made.thirdsubmission.ui.leaguedetail.DetailLeagueActivity
import com.ilham.made.thirdsubmission.ui.matchdetail.DetailMatchActivity
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() {

    private val nextMatchViewModel: NextMatchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(this@NextMatchFragment, NextMatchViewModel.Factory(activity.application))
            .get(NextMatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        nextMatchViewModel.setLeagueId(DetailLeagueActivity.leagueId)
        nextMatchViewModel.getNextMatch()?.observe(viewLifecycleOwner, Observer {
            rv_next_match.adapter?.let { adapter ->
                when (adapter) {
                    is MatchAdapter -> {
                        adapter.setMatch(it)
                        adapter.setMatchClickCallback(object : MatchAdapter.MatchClickCallback {
                            override fun onItemClicked(data: MatchModel) {
                                startActivity<DetailMatchActivity>(DetailMatchActivity.EXTRA_DATA to data)
                            }
                        })
                    }
                }
            }
        })

        nextMatchViewModel.nextMatchState.observer(viewLifecycleOwner, Observer {
            getUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MatchAdapter(mutableListOf())
        }
    }

    private fun getUIState(it: NextMatchState) {
        when (it) {
            is NextMatchState.IsLoading -> {
                isLoading(it.state)
            }
            is NextMatchState.Error -> {
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
