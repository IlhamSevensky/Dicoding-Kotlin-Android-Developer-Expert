package com.ilham.made.footballclub.presenter

import com.google.gson.Gson
import com.ilham.made.footballclub.`interface`.TeamsView
import com.ilham.made.footballclub.api.TheSportDBApi
import com.ilham.made.footballclub.repository.ApiRepository
import com.ilham.made.footballclub.response.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}