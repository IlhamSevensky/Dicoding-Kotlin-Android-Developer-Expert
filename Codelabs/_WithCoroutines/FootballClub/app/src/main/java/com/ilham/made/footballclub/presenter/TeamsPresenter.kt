package com.ilham.made.footballclub.presenter

import com.google.gson.Gson
import com.ilham.made.footballclub.`interface`.TeamsView
import com.ilham.made.footballclub.api.TheSportDBApi
import com.ilham.made.footballclub.repository.ApiRepository
import com.ilham.made.footballclub.response.TeamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getTeamList(league: String?) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams(league)).await(),
                TeamResponse::class.java)

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }
}