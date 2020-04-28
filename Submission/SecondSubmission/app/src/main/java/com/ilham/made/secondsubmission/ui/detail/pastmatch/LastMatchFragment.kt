package com.ilham.made.secondsubmission.ui.detail.pastmatch

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
import com.ilham.made.secondsubmission.adapters.LastMatchAdapter
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.ui.detail.DetailLeagueActivity
import com.ilham.made.secondsubmission.ui.match.MatchDetailActivity
import kotlinx.android.synthetic.main.fragment_past_match.*
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
        return inflater.inflate(R.layout.fragment_past_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        lastMatchViewModel.getListLastMatchByLeagueFromApi(DetailLeagueActivity.leagueId)
        lastMatchViewModel.getListLastMatch()?.observe(viewLifecycleOwner, Observer {
            rv_last_match.adapter.let { adapter ->
                when (adapter) {
                    is LastMatchAdapter -> {
                        adapter.setMatch(it)
                        adapter.setOnItemClickCallback(object :
                            LastMatchAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: MatchModel) {
                                startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_DATA to data)
                            }

                        })
                    }
                }
            }
        })

        lastMatchViewModel.getLastMatchState().observer(viewLifecycleOwner, Observer {
            handleUIState(it)
        })

    }

    private fun setupRecyclerView() {
        rv_last_match.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = LastMatchAdapter(mutableListOf())
        }
    }

    private fun handleUIState(it: LastMatchState) {
        when (it) {
            is LastMatchState.IsLoading -> {
                isLoading(it.state)
            }
            is LastMatchState.Error -> {
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
