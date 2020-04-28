package com.ilham.made.footballclub.presenter

import com.google.gson.Gson
import com.ilham.made.footballclub.`interface`.TeamDetailView
import com.ilham.made.footballclub.api.TheSportDBApi
import com.ilham.made.footballclub.repository.ApiRepository
import com.ilham.made.footballclub.response.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson
) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(data.teams)
            }
        }
    }
}