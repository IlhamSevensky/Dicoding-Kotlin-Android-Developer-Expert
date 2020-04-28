package com.ilham.made.footballclub.`interface`

import com.ilham.made.footballclub.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}