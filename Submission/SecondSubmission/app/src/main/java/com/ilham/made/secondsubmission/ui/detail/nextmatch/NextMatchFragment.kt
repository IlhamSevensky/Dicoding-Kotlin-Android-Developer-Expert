package com.ilham.made.secondsubmission.ui.detail.nextmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.adapters.NextMatchAdapter
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.ui.detail.DetailLeagueActivity
import com.ilham.made.secondsubmission.ui.match.MatchDetailActivity
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

        nextMatchViewModel.getListNextMatchByLeagueFromApi(DetailLeagueActivity.leagueId)
        nextMatchViewModel.getListNextMatch()?.observe(viewLifecycleOwner, Observer {
            rv_next_match.adapter.let { adapter ->
                when (adapter) {
                    is NextMatchAdapter -> {
                        adapter.setMatch(it)
                        adapter.setOnItemClickCallback(object :
                            NextMatchAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: MatchModel) {
                                startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_DATA to data)
                            }

                        })
                    }
                }
            }
        })

        nextMatchViewModel.getNextMatchState().observer(viewLifecycleOwner, Observer {
            handleUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NextMatchAdapter(mutableListOf())
        }
    }

    private fun handleUIState(it: NextMatchState) {
        when (it) {
            is NextMatchState.IsLoading -> {
                isLoading(it.state)
            }
            is NextMatchState.Error -> {
                isLoading(false)
                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
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
