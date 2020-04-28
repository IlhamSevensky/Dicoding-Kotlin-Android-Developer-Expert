package com.ilham.made.thirdsubmission.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.adapter.MatchAdapter
import com.ilham.made.thirdsubmission.data.repository.SearchMatchState
import com.ilham.made.thirdsubmission.model.MatchModel
import com.ilham.made.thirdsubmission.ui.matchdetail.DetailMatchActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this@SearchActivity) {
        }

        ViewModelProvider(this@SearchActivity, SearchViewModel.Factory(activity.application))
            .get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupRecyclerView()
        setupSearchView()

        searchViewModel.getListSearchMatch()?.observe(this@SearchActivity, Observer {
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

        searchViewModel.searchMatchState.observer(this@SearchActivity, Observer {
            getUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_search_match.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
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
                        searchViewModel.searchMatch(currentQuery)
                        searchViewModel.getListSearchMatch()?.observe(this@SearchActivity, Observer {
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
                Toast.makeText(this@SearchActivity, it.error, Toast.LENGTH_LONG).show()
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
