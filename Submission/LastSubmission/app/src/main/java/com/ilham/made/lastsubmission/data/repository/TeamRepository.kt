package com.ilham.made.lastsubmission.data.repository

import androidx.lifecycle.MutableLiveData
import com.ilham.made.lastsubmission.api.ApiClient
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.utils.EspressoIdlingResource
import com.ilham.made.lastsubmission.utils.SingleLiveEvent
import kotlinx.coroutines.*
import okio.IOException
import retrofit2.await

object TeamRepository {

    private var teamRepositoryJob: CompletableJob? = null

    private var teamState: SingleLiveEvent<TeamState> = SingleLiveEvent()
    private var searchTeamState: SingleLiveEvent<SearchTeamState> = SingleLiveEvent()
    private var dataSearchTeamTemporary = ArrayList<TeamModel>()

    fun getTeamByLeagueFromApi(leagueId: Int): MutableLiveData<List<TeamModel>> {
        teamRepositoryJob = Job()
        return object : MutableLiveData<List<TeamModel>>() {
            override fun onActive() {
                super.onActive()
                teamRepositoryJob?.let { job ->
                    CoroutineScope(Dispatchers.IO + job).launch {
                        try {
                            teamState.postValue(TeamState.IsLoading(true))

                            val response = ApiClient.instance.getListTeamByLeague(leagueId).await()

                            withContext(Dispatchers.Main) {
                                value = response.result
                                teamState.value = TeamState.IsLoading(false)
                            }

                        } catch (error: IOException) {
                            error.printStackTrace()
                            teamState.postValue(TeamState.Error("Connection time out"))
                        }
                    }
                }
            }
        }
    }

    fun searchTeamByTeamFromApi(query: String): MutableLiveData<List<TeamModel>> {
        teamRepositoryJob = Job()
        val data = MutableLiveData<List<TeamModel>>()
        teamRepositoryJob?.let { job ->
            CoroutineScope(Dispatchers.IO + job).launch {
                try {
                    searchTeamState.postValue(SearchTeamState.IsLoading(true))
                    dataSearchTeamTemporary.clear()

                    val response =
                        ApiClient.instance.searchTeamByQuery(query).await()

                    if (response.result != null) {
                        var iteration = 0
                        while (iteration < response.result.size) {
                            if (response.result[iteration].strSport.equals(
                                    "Soccer",
                                    ignoreCase = true
                                )
                            ) {
                                dataSearchTeamTemporary.add(response.result[iteration])

                            }
                            iteration++
                        }

                        if (dataSearchTeamTemporary.isEmpty()) {
                            searchTeamState.postValue(SearchTeamState.Error("Team Not Found"))
                        } else {
                            withContext(Dispatchers.Main) {
                                data.value = dataSearchTeamTemporary
                                EspressoIdlingResource.decrement()
                                searchTeamState.value =
                                    SearchTeamState.IsLoading(false)
                            }
                        }

                    } else {
                        searchTeamState.postValue(SearchTeamState.Error("Team Not Found"))
                    }

                } catch (error: IOException) {
                    error.printStackTrace()
                    searchTeamState.postValue(SearchTeamState.Error("Connection time out"))
                }
                job.complete()
            }
        }
        return data
    }

    fun getTeamState() = teamState
    fun getSearchTeamState() = searchTeamState

}

sealed class TeamState {
    data class IsLoading(var state: Boolean = false) : TeamState()
    data class Error(var error: String?) : TeamState()
}

sealed class SearchTeamState {
    data class IsLoading(var state: Boolean = false) : SearchTeamState()
    data class Error(var error: String?) : SearchTeamState()
}