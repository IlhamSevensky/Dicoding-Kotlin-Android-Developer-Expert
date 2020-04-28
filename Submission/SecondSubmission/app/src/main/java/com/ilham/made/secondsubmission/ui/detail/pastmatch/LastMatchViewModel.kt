package com.ilham.made.secondsubmission.ui.detail.pastmatch

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

class LastMatchViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = LastMatchViewModel::class.java.simpleName
    }

    private var state: SingleLiveEvent<LastMatchState> = SingleLiveEvent()

    private var listLastMatch = MutableLiveData<List<MatchModel>>()

    private var temporaryMatch = ArrayList<MatchModel>()

    fun getListLastMatchByLeagueFromApi(leagueId: Int) {
        GlobalScope.launch {
            if (listLastMatch.value == null) {
                temporaryMatch.clear()
                state.postValue(LastMatchState.IsLoading(true))
                try {
                    val request = ApiClient.instance.getListPastMatchByLeague(leagueId).await()

                    if (request.result != null) {
                        temporaryMatch.addAll(request.result)
                        var x = 0
                        while (x < temporaryMatch.size) {
                            try {
                                if (temporaryMatch[x].idHomeTeam != null) {
                                    val homeTeamBadge =
                                        ApiClient.instance.getListTeam(temporaryMatch[x].idHomeTeam!!)
                                            .await()

                                    temporaryMatch[x].strBadgeHomeTeam =
                                        homeTeamBadge.result?.get(0)?.strTeamBadge
                                }

                                if (temporaryMatch[x].idAwayTeam != null) {
                                    val awayTeamBadge =
                                        ApiClient.instance.getListTeam(temporaryMatch[x].idAwayTeam!!)
                                            .await()



                                    temporaryMatch[x].strBadgeAwayTeam =
                                        awayTeamBadge.result?.get(0)?.strTeamBadge
                                }

                                x++
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        val data = temporaryMatch as List<MatchModel>
                        listLastMatch.postValue(data)

                        state.postValue(LastMatchState.IsLoading(false))
                    } else {
                        state.postValue(LastMatchState.Error("Last Match Not Found"))
                    }

                } catch (networkError: IOException) {
                    state.postValue(LastMatchState.Error("Connection time out"))
                }
            }
        }
    }

    fun getLastMatchState() = state

    fun getListLastMatch(): LiveData<List<MatchModel>>? = listLastMatch

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LastMatchViewModel Cleared")
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LastMatchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LastMatchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }
}

sealed class LastMatchState {
    data class IsLoading(var state: Boolean = false) : LastMatchState()
    data class Error(var error: String?) : LastMatchState()
}