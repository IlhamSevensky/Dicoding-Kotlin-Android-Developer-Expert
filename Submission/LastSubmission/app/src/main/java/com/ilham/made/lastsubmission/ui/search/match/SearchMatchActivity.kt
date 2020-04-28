package com.ilham.made.lastsubmission.ui.search.match

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
import com.ilham.made.lastsubmission.adapter.MatchAdapter
import com.ilham.made.lastsubmission.data.repository.SearchMatchState
import com.ilham.made.lastsubmission.model.MatchModel
import com.ilham.made.lastsubmission.ui.matchdetail.DetailMatchActivity
import kotlinx.android.synthetic.main.activity_search_match.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity() {

    private val searchMatchViewModel: SearchMatchViewModel by lazy {
        val activity = requireNotNull(this@SearchMatchActivity) {
        }

        ViewModelProvider(
            this@SearchMatchActivity,
            SearchMatchViewModel.Factory(
                activity.application
            )
        )
            .get(SearchMatchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        setupRecyclerView()
        setupSearchView()

        searchMatchViewModel.getListSearchMatch()?.observe(this@SearchMatchActivity, Observer {
            rv_search_match.adapter?.let { adapter ->
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

        searchMatchViewModel.searchMatchState.observer(this@SearchMatchActivity, Observer {
            getUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_search_match.apply {
            layoutManager = LinearLayoutManager(this@SearchMatchActivity)
            adapter = MatchAdapter(mutableListOf())
        }
    }

    private fun setupSearchView() {
        with(search_view) {
            setSearchHint(resources.getString(R.string.search_match))

            setOnHomeActionClickListener {
                finish()
            }

            setOnSearchListener(object : FloatingSearchView.OnSearchListener {
                override fun onSearchAction(currentQuery: String?) {
                    if (!currentQuery.isNullOrEmpty()) {
                        searchMatchViewModel.searchMatch(currentQuery)
                        searchMatchViewModel.getListSearchMatch()
                            ?.observe(this@SearchMatchActivity, Observer {
                                rv_search_match.adapter?.let { adapter ->
                                    when (adapter) {
                                        is MatchAdapter -> {
                                            adapter.setMatch(it)
                                            adapter.setMatchClickCallback(object :
                                                MatchAdapter.MatchClickCallback {
                                                override fun onItemClicked(data: MatchModel) {
                                                    startActivity<DetailMatchActivity>(
                                                        DetailMatchActivity.EXTRA_DATA to data
                                                    )
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

    private fun getUIState(it: SearchMatchState) {
        when (it) {
            is SearchMatchState.IsLoading -> {
                isLoading(it.state)
            }
            is SearchMatchState.Error -> {
                isLoading(false)
                Toast.makeText(this@SearchMatchActivity, it.error, Toast.LENGTH_LONG).show()
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
