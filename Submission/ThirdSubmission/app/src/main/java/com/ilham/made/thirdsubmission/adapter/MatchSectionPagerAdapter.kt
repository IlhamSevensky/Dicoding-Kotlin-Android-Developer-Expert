package com.ilham.made.thirdsubmission.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.ui.leaguedetail.content.lastmatch.LastMatchFragment
import com.ilham.made.thirdsubmission.ui.leaguedetail.content.nextmatch.NextMatchFragment

class MatchSectionPageAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitle = intArrayOf(R.string.tab_title_past_match, R.string.tab_title_next_match)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = LastMatchFragment()
            1 -> fragment = NextMatchFragment()
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(tabTitle[position])

    override fun getCount(): Int = tabTitle.size

}