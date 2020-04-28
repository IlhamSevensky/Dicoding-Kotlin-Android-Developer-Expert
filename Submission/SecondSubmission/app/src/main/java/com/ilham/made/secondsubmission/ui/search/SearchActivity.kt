package com.ilham.made.secondsubmission.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.adapters.NextMatchAdapter
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.ui.match.MatchDetailActivity
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
            rv_search_event.adapter.let { adapter ->
                if (adapter is NextMatchAdapter) {
                    adapter.setMatch(it)
                    adapter.setOnItemClickCallback(object : NextMatchAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: MatchModel) {
                            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_DATA to data)
                        }

                    })
                }
            }
        })

        searchViewModel.getSearchState().observer(this@SearchActivity, Observer {
            handleUIState(it)
        })
    }

    private fun setupRecyclerView() {
        rv_search_event.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = NextMatchAdapter(mutableListOf())
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
                        searchViewModel.searchMatchByQueryFromApi(currentQuery)
                    }
                }

                override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                    // Nothing
                }

            })
        }
    }

    private fun handleUIState(it: SearchState) {
        when (it) {
            is SearchState.IsLoading -> {
                isLoading(it.state)
            }
            is SearchState.Error -> {
                isLoading(false)
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
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
