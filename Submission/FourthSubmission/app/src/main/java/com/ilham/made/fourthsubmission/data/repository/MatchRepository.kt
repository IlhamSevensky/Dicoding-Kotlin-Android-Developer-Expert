package com.ilham.made.fourthsubmission.data.repository

import androidx.lifecycle.MutableLiveData
import com.ilham.made.fourthsubmission.api.ApiClient
import com.ilham.made.fourthsubmission.model.MatchModel
import com.ilham.made.fourthsubmission.utils.SingleLiveEvent
import kotlinx.coroutines.*
import okio.IOException
import retrofit2.await

object MatchRepository {

    private var matchRepositoryJob: CompletableJob? = null

    private var lastMatchState: SingleLiveEvent<LastMatchState> = SingleLiveEvent()
    private var nextMatchState: SingleLiveEvent<NextMatchState> = SingleLiveEvent()
    private var searchMatchState: SingleLiveEvent<SearchMatchState> = SingleLiveEvent()

    private var dataLastMatchTemporary = ArrayList<MatchModel>()
    private var dataNextMatchTemporary = ArrayList<MatchModel>()
    private var dataSearchMatchTemporary = ArrayList<MatchModel>()

    fun getLastMatchByLeagueFromApi(leagueId: Int): MutableLiveData<List<MatchModel>> {
        matchRepositoryJob = Job()
        return object : MutableLiveData<List<MatchModel>>() {
            override fun onActive() {
                super.onActive()
                matchRepositoryJob?.let { job ->
                    CoroutineScope(Dispatchers.IO + job).launch {
                        try {
                            lastMatchState.postValue(LastMatchState.IsLoading(true))
                            dataLastMatchTemporary.clear()

                            val response =
                                ApiClient.instance.getListPastMatchByLeague(leagueId).await()

                            if (response.result != null) {
                                dataLastMatchTemporary.addAll(response.result)

                                GlobalScope.launch {
                                    withContext(Dispatchers.IO) {
                                        var iteration = 0
                                        while (iteration < dataLastMatchTemporary.size) {
                                            try {
                                                if (dataLastMatchTemporary[iteration].idHomeTeam != null) {
                                                    val homeTeamBadge =
                                                        ApiClient.instance.getListTeamByTeamId(
                                                            dataLastMatchTemporary[iteration].idHomeTeam!!
                                                        )
                                                            .await()

                                                    dataLastMatchTemporary[iteration].strBadgeHomeTeam =
                                                        homeTeamBadge.result?.get(0)?.strTeamBadge
                                                }

                                                if (dataLastMatchTemporary[iteration].idAwayTeam != null) {
                                                    val awayTeamBadge =
                                                        ApiClient.instance.getListTeamByTeamId(
                                                            dataLastMatchTemporary[iteration].idAwayTeam!!
                                                        )
                                                            .await()

                                                    dataLastMatchTemporary[iteration].strBadgeAwayTeam =
                                                        awayTeamBadge.result?.get(0)?.strTeamBadge
                                                }

                                                iteration++

                                            } catch (error: Exception) {
                                                error.printStackTrace()
                                            }
                                        }
                                        withContext(Dispatchers.Main) {
                                            value = dataLastMatchTemporary
                                            lastMatchState.value = LastMatchState.IsLoading(false)
                                        }
                                    }
                                }

                            } else {
                                lastMatchState.postValue(LastMatchState.Error("Last Match Not Found"))
                            }

                        } catch (error: IOException) {
                            error.printStackTrace()
                            lastMatchState.postValue(LastMatchState.Error("Connection time out"))
                        }
                        job.complete()
                    }
                }

            }
        }
    }

    fun getNextMatchByLeagueFromApi(leagueId: Int): MutableLiveData<List<MatchModel>> {
        matchRepositoryJob = Job()
        return object : MutableLiveData<List<MatchModel>>() {
            override fun onActive() {
                super.onActive()
                matchRepositoryJob?.let { job ->
                    CoroutineScope(Dispatchers.IO + job).launch {
                        try {
                            nextMatchState.postValue(NextMatchState.IsLoading(true))
                            dataNextMatchTemporary.clear()

                            val response =
                                ApiClient.instance.getListNextMatchByLeague(leagueId).await()

                            if (response.result != null) {
                                dataNextMatchTemporary.addAll(response.result)

                                GlobalScope.launch {
                                    withContext(Dispatchers.IO) {
                                        var iteration = 0
                                        while (iteration < dataNextMatchTemporary.size) {
                                            try {
                                                if (dataNextMatchTemporary[iteration].idHomeTeam != null) {
                                                    val homeTeamBadge =
                                                        ApiClient.instance.getListTeamByTeamId(
                                                            dataNextMatchTemporary[iteration].idHomeTeam!!
                                                        )
                                                            .await()

                                                    dataNextMatchTemporary[iteration].strBadgeHomeTeam =
                                                        homeTeamBadge.result?.get(0)?.strTeamBadge
                                                }

                                                if (dataNextMatchTemporary[iteration].idAwayTeam != null) {
                                                    val awayTeamBadge =
                                                        ApiClient.instance.getListTeamByTeamId(
                                                            dataNextMatchTemporary[iteration].idAwayTeam!!
                                                        )
                                                            .await()

                                                    dataNextMatchTemporary[iteration].strBadgeAwayTeam =
                                                        awayTeamBadge.result?.get(0)?.strTeamBadge
                                                }

                                                iteration++

                                            } catch (error: Exception) {
                                                error.printStackTrace()
                                            }
                                        }
                                        withContext(Dispatchers.Main) {
                                            value = dataNextMatchTemporary
                                            nextMatchState.value = NextMatchState.IsLoading(false)
                                        }
                                    }
                                }


                            } else {
                                nextMatchState.postValue(NextMatchState.Error("Next Match Not Found"))
                            }

                        } catch (error: IOException) {
                            error.printStackTrace()
                            nextMatchState.postValue(NextMatchState.Error("Connection time out"))
                        }
                        job.complete()
                    }
                }

            }
        }
    }

    fun searchMatchByTeamFromApi(query: String): MutableLiveData<List<MatchModel>> {
        matchRepositoryJob = Job()
        val data = MutableLiveData<List<MatchModel>>()
        matchRepositoryJob?.let { job ->
            CoroutineScope(Dispatchers.IO + job).launch {
                try {
                    searchMatchState.postValue(SearchMatchState.IsLoading(true))
                    dataSearchMatchTemporary.clear()

                    val response =
                        ApiClient.instance.searchMatchByQuery(query).await()

                    if (response.result != null) {
                        var iteration = 0
                        while (iteration < response.result.size) {
                            if (response.result[iteration].strSport.equals(
                                    "Soccer",
                                    ignoreCase = true
                                )
                            ) {
                                dataSearchMatchTemporary.add(response.result[iteration])

                            }
                            iteration++
                        }

                        try {
                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    var _iteration = 0
                                    while (_iteration < dataSearchMatchTemporary.size) {
                                        if (!dataSearchMatchTemporary[_iteration].idHomeTeam?.toString()
                                                .isNullOrEmpty()
                                        ) {
                                            val homeTeamBadge =
                                                ApiClient.instance.getListTeamByTeamId(
                                                    dataSearchMatchTemporary[_iteration].idHomeTeam!!
                                                )
                                                    .await()

                                            dataSearchMatchTemporary[_iteration].strBadgeHomeTeam =
                                                homeTeamBadge.result?.get(0)?.strTeamBadge

                                        }

                                        if (!dataSearchMatchTemporary[_iteration].idAwayTeam?.toString()
                                                .isNullOrEmpty()
                                        ) {
                                            val awayTeamBadge =
                                                ApiClient.instance.getListTeamByTeamId(
                                                    dataSearchMatchTemporary[_iteration].idAwayTeam!!
                                                )
                                                    .await()

                                            dataSearchMatchTemporary[_iteration].strBadgeAwayTeam =
                                                awayTeamBadge.result?.get(0)?.strTeamBadge
                                        }
                                        _iteration++
                                    }
                                    if (dataSearchMatchTemporary.isEmpty()) {
                                        searchMatchState.postValue(SearchMatchState.Error("Match Not Found"))
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            data.value = dataSearchMatchTemporary
                                            searchMatchState.value =
                                                SearchMatchState.IsLoading(false)
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else {
                        searchMatchState.postValue(SearchMatchState.Error("Match Not Found"))
                    }

                } catch (error: IOException) {
                    error.printStackTrace()
                    searchMatchState.postValue(SearchMatchState.Error("Connection time out"))
                }
                job.complete()
            }
        }
        return data
    }

    fun getLastMatchState() = lastMatchState
    fun getNextMatchState() = nextMatchState
    fun getSearchMatchState() = searchMatchState

}

sealed class LastMatchState {
    data class IsLoading(var state: Boolean = false) : LastMatchState()
    data class Error(var error: String?) : LastMatchState()
}

sealed class NextMatchState {
    data class IsLoading(var state: Boolean = false) : NextMatchState()
    data class Error(var error: String?) : NextMatchState()
}

sealed class SearchMatchState {
    data class IsLoading(var state: Boolean = false) : SearchMatchState()
    data class Error(var error: String?) : SearchMatchState()
}