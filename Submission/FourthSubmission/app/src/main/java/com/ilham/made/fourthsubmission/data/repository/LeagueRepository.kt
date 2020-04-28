package com.ilham.made.fourthsubmission.data.repository

import androidx.lifecycle.MutableLiveData
import com.ilham.made.fourthsubmission.api.ApiClient
import com.ilham.made.fourthsubmission.model.LeagueModel
import com.ilham.made.fourthsubmission.utils.SingleLiveEvent
import kotlinx.coroutines.*
import okio.IOException
import retrofit2.await

object LeagueRepository {

    private var leagueRepositoryJob: CompletableJob? = null

    private var state: SingleLiveEvent<LeagueState> = SingleLiveEvent()

    private var detailState: SingleLiveEvent<DetailLeagueState> = SingleLiveEvent()

    private var dataTemporary = ArrayList<LeagueModel>()

    fun getLeagueListFromApi(): MutableLiveData<List<LeagueModel>> {
        leagueRepositoryJob = Job()
        return object : MutableLiveData<List<LeagueModel>>() {
            override fun onActive() {
                super.onActive()
                leagueRepositoryJob?.let { job ->
                    CoroutineScope(Dispatchers.IO + job).launch {
                        try {
                            state.postValue(LeagueState.IsLoading(true))
                            dataTemporary.clear()
                            val leagueData = ApiClient.instance.getListLeague()
                            val leagueResult = leagueData.await().result

                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    var iteration = 0
                                    while (iteration < leagueResult?.size!!) {
                                        if (iteration == 20) {
                                            break
                                        } else {
                                            if (leagueResult[iteration].strSport.equals(
                                                    "Soccer",
                                                    ignoreCase = true
                                                )
                                            ) {
                                                dataTemporary.add(leagueResult[iteration])
                                            }
                                            iteration++
                                        }
                                    }

                                    var _iteration = 0
                                    while (_iteration < dataTemporary.size) {
                                        val leagueBadge =
                                            ApiClient.instance.getLeagueDetail(dataTemporary[_iteration].idLeague)
                                                .await()

                                        dataTemporary[_iteration].strBadge =
                                            leagueBadge.result?.get(0)?.strBadge
                                        _iteration++
                                    }

                                    withContext(Dispatchers.Main) {
                                        value = dataTemporary
                                        state.value = LeagueState.IsLoading(false)
                                    }
                                }
                            }
                        } catch (error: IOException) {
                            error.printStackTrace()
                            state.postValue(LeagueState.Error("Connection time out"))
                        }
                        job.complete()
                    }
                }
            }
        }
    }

    fun getDetailLeagueFromApi(leagueId: Int): MutableLiveData<List<LeagueModel>> {
        leagueRepositoryJob = Job()
        return object : MutableLiveData<List<LeagueModel>>() {
            override fun onActive() {
                super.onActive()
                leagueRepositoryJob?.let { job ->
                    CoroutineScope(Dispatchers.IO + job).launch {
                        try {
                            detailState.postValue(DetailLeagueState.IsLoading(true))

                            val response = ApiClient.instance.getLeagueDetail(leagueId).await()

                            withContext(Dispatchers.Main) {
                                value = response.result
                                detailState.value = DetailLeagueState.IsLoading(false)
                                job.complete()
                            }
                        } catch (error: IOException) {
                            error.printStackTrace()
                            detailState.postValue(DetailLeagueState.Error("Connection time out"))
                        }
                    }
                }

            }
        }
    }

    fun getLeagueState() = state
    fun getDetailLeagueState() = detailState

}

sealed class LeagueState {
    data class IsLoading(var state: Boolean = false) : LeagueState()
    data class Error(var error: String?) : LeagueState()
}

sealed class DetailLeagueState {
    data class IsLoading(var state: Boolean = false) : DetailLeagueState()
    data class Error(var error: String?) : DetailLeagueState()
}