package com.ilham.made.footballclub.presenter

import com.google.gson.Gson
import com.ilham.made.footballclub.`interface`.TeamDetailView
import com.ilham.made.footballclub.api.TheSportDBApi
import com.ilham.made.footballclub.repository.ApiRepository
import com.ilham.made.footballclub.response.TeamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson
) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data =gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(teamId)).await(),
                TeamResponse::class.java)

            view.showTeamDetail(data.teams)
            view.hideLoading()
        }

    }
}