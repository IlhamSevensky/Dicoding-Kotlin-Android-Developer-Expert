package com.ilham.made.footballclub.`interface`

import com.ilham.made.footballclub.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}