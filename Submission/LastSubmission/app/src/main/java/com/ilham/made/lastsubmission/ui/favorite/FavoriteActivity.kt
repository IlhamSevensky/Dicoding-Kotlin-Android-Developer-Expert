package com.ilham.made.lastsubmission.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.adapter.FavoriteSectionPageAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setupTab()
        setupToolbar()
    }

    private fun setupTab() {
        val favoriteSectionPagerAdapter =
            FavoriteSectionPageAdapter(this@FavoriteActivity, supportFragmentManager)
        view_pager_favorite.adapter = favoriteSectionPagerAdapter
        tab_layout_favorite.setupWithViewPager(view_pager_favorite)
    }

    private fun setupToolbar() {
        setSupportActionBar(favorite_match_toolbar)
        supportActionBar?.title = resources.getString(R.string.favorite_toolbar)
        supportActionBar?.elevation = 0F
        favorite_match_toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        favorite_match_toolbar.setNavigationOnClickListener { finish() }
    }
}
