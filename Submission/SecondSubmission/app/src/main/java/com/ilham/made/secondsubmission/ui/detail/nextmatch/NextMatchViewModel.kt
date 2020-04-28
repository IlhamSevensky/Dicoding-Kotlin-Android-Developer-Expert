package com.ilham.made.secondsubmission.ui.detail.nextmatch

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

class NextMatchViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = NextMatchViewModel::class.java.simpleName
    }

    private var state: SingleLiveEvent<NextMatchState> = SingleLiveEvent()

    private var listNextMatch = MutableLiveData<List<MatchModel>>()

    private var temporaryMatch = ArrayList<MatchModel>()

    fun getListNextMatchByLeagueFromApi(leagueId: Int) {
        GlobalScope.launch {
            if (listNextMatch.value == null) {
                temporaryMatch.clear()
                state.postValue(NextMatchState.IsLoading(true))
                try {
                    val request =
                        ApiClient.instance.getListNextMatchByLeague(leagueId).await()

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
                        listNextMatch.postValue(data)

                        state.postValue(NextMatchState.IsLoading(false))

                    } else {
                        state.postValue(NextMatchState.Error("Next Match Not Found"))
                    }

                } catch (networkError: IOException) {
                    state.postValue(NextMatchState.Error("Connection time out"))
                }
            }
        }
    }

    fun getNextMatchState() = state

    fun getListNextMatch(): LiveData<List<MatchModel>>? = listNextMatch

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LastMatchViewModel Cleared")
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NextMatchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NextMatchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }
}

sealed class NextMatchState {
    data class IsLoading(var state: Boolean = false) : NextMatchState()
    data class Error(var error: String?) : NextMatchState()
}