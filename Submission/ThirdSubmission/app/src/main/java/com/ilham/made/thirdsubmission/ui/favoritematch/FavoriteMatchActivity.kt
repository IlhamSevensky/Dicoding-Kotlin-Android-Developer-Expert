package com.ilham.made.thirdsubmission.ui.favoritematch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.adapter.FavoriteMatchAdapter
import com.ilham.made.thirdsubmission.model.MatchFavoriteModel
import com.ilham.made.thirdsubmission.ui.matchdetail.DetailMatchActivity
import com.ilham.made.thirdsubmission.utils.Helper
import kotlinx.android.synthetic.main.activity_favorite_match.*
import org.jetbrains.anko.startActivity

class FavoriteMatchActivity : AppCompatActivity() {

    private val favoriteMatchViewModel: FavoriteMatchViewModel by lazy {
        val activity = requireNotNull(this@FavoriteMatchActivity) {
        }

        ViewModelProvider(
            this@FavoriteMatchActivity,
            FavoriteMatchViewModel.Factory(activity.application)
        )
            .get(FavoriteMatchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)
        setupToolbar()
        setupRecyclerView()

        favoriteMatchViewModel.favoriteMatch?.observe(this@FavoriteMatchActivity, Observer {
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

    private fun setupToolbar() {
        setSupportActionBar(favorite_match_toolbar)
        supportActionBar?.title = resources.getString(R.string.match_favorite_menu)
        supportActionBar?.elevation = 8F
        favorite_match_toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        favorite_match_toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        rv_favorite_match.apply {
            layoutManager = LinearLayoutManager(this@FavoriteMatchActivity)
            adapter = FavoriteMatchAdapter(mutableListOf())
        }
    }
}
