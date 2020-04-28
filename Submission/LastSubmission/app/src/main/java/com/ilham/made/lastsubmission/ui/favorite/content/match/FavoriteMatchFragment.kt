package com.ilham.made.lastsubmission.ui.favorite.content.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.adapter.FavoriteMatchAdapter
import com.ilham.made.lastsubmission.model.MatchFavoriteModel
import com.ilham.made.lastsubmission.ui.matchdetail.DetailMatchActivity
import com.ilham.made.lastsubmission.utils.Helper
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMatchFragment : Fragment() {

    private val favoriteMatchViewModel: FavoriteMatchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }

        ViewModelProvider(
            this@FavoriteMatchFragment,
            FavoriteMatchViewModel.Factory(activity.application)
        )
            .get(FavoriteMatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        favoriteMatchViewModel.favoriteMatch?.observe(viewLifecycleOwner, Observer {
            rv_favorite_match.adapter?.let {adapter ->
                when (adapter) {
                    is FavoriteMatchAdapter -> {
                        adapter.setMatch(it)
                        adapter.setMatchClickCallback(object : FavoriteMatchAdapter.MatchClickCallback{
                            override fun onItemClicked(data: MatchFavoriteModel) {
                                val match = Helper.convertFavoriteMatchModelToMatchModel(data)
                                startActivity<DetailMatchActivity>(DetailMatchActivity.EXTRA_DATA to match)
                            }
                        })
                    }
                }
            }
        })

    }

    private fun setupRecyclerView() {
        rv_favorite_match.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FavoriteMatchAdapter(mutableListOf())
        }
    }

}
