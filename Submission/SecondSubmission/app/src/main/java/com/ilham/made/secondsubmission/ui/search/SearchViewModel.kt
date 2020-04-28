package com.ilham.made.secondsubmission.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.utils.SingleLiveEvent
import com.ilham.made.secondsubmission.webservices.ApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }

    private var state: SingleLiveEvent<SearchState> = SingleLiveEvent()

    private var listSearchMatch = MutableLiveData<List<MatchModel>>()

    private var temporarySearchMatch = ArrayList<MatchModel>()

    fun searchMatchByQueryFromApi(query: String) {
        GlobalScope.launch {
            temporarySearchMatch.clear()
            state.postValue(SearchState.IsLoading(true))
            try {
                val request = ApiClient.instance.searchMatchByQuery(query).await()

                if (request.result != null) {
                    var y = 0
                    while (y < request.result.size) {
                        if (request.result[y].strSport.equals("Soccer", ignoreCase = true)) {
                            temporarySearchMatch.add(request.result[y])

                        }
                        y++
                    }

                    try {
                        var x = 0
                        while (x < temporarySearchMatch.size){
                            if (temporarySearchMatch[x].idHomeTeam != null) {
                                val homeTeamBadge =
                                    ApiClient.instance.getListTeam(temporarySearchMatch[x].idHomeTeam!!)
                                        .await()

                                temporarySearchMatch[x].strBadgeHomeTeam =
                                    homeTeamBadge.result?.get(0)?.strTeamBadge

                            }

                            if (temporarySearchMatch[x].idAwayTeam != null) {
                                val awayTeamBadge =
                                    ApiClient.instance.getListTeam(temporarySearchMatch[x].idAwayTeam!!)
                                        .await()

                                temporarySearchMatch[x].strBadgeAwayTeam =
                                    awayTeamBadge.result?.get(0)?.strTeamBadge
                            }
                            x++
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    val data = temporarySearchMatch as List<MatchModel>
                    if (data.isEmpty()) {
                        state.postValue(SearchState.Error("Match Not Found"))
                    }
                    listSearchMatch.postValue(data)
                } else {
                    state.postValue(SearchState.Error("Match Not Found"))
                }

            } catch (networkError: IOException) {
                state.postValue(SearchState.Error(networkError.message.toString()))
            }
            state.postValue(SearchState.IsLoading(false))
        }
    }

    fun getSearchState() = state

    fun getListSearchMatch(): LiveData<List<MatchModel>>? = listSearchMatch

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LastMatchViewModel Cleared")
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }
}

sealed class SearchState {
    data class IsLoading(var state: Boolean = false) : SearchState()
    data class Error(var error: String?) : SearchState()
}