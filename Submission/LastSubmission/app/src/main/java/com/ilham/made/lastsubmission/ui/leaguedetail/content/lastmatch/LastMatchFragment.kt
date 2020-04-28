package com.ilham.made.lastsubmission.ui.leaguedetail.content.lastmatch

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
import com.ilham.made.lastsubmission.adapter.MatchAdapter
import com.ilham.made.lastsubmission.data.repository.LastMatchState
import com.ilham.made.lastsubmission.model.MatchModel
import com.ilham.made.lastsubmission.ui.leaguedetail.DetailLeagueActivity
import com.ilham.made.lastsubmission.ui.matchdetail.DetailMatchActivity
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment : Fragment() {

    private val lastMatchViewModel: LastMatchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(this@LastMatchFragment, LastMatchViewModel.Factory(activity.application))
            .get(LastMatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        lastMatchViewModel.setLeagueId(DetailLeagueActivity.leagueId)
        lastMatchViewModel.getLastMatch()?.observe(viewLifecycleOwner, Observer {
            rv_last_match.adapter?.let { adapter ->
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

        lastMatchViewModel.lastMatchState.observer(viewLifecycleOwner, Observer {
            getUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_last_match.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MatchAdapter(mutableListOf())
        }
    }

    private fun getUIState(it: LastMatchState) {
        when (it) {
            is LastMatchState.IsLoading -> {
                isLoading(it.state)
            }
            is LastMatchState.Error -> {
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
