package com.ilham.made.lastsubmission.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.ui.favorite.content.match.FavoriteMatchFragment
import com.ilham.made.lastsubmission.ui.favorite.content.team.FavoriteTeamFragment

class FavoriteSectionPageAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitle = intArrayOf(R.string.tab_title_favorite_match, R.string.tab_title_favorite_team)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FavoriteMatchFragment()
            1 -> fragment = FavoriteTeamFragment()
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(tabTitle[position])

    override fun getCount(): Int = tabTitle.size

}