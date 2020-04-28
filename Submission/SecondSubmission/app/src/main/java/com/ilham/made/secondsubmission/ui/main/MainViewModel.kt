package com.ilham.made.secondsubmission.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ilham.made.secondsubmission.models.LeagueModel
import com.ilham.made.secondsubmission.utils.SingleLiveEvent
import com.ilham.made.secondsubmission.webservices.ApiClient
import kotlinx.coroutines.*
import retrofit2.await
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var state: SingleLiveEvent<LeagueState> = SingleLiveEvent()

    private var dataTemporary = ArrayList<LeagueModel>()

    private var listLeague = MutableLiveData<List<LeagueModel>>()

    init {
        getListLeagueFromApi()
    }

    private fun getListLeagueFromApi() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.postValue(LeagueState.IsLoading(true))
                try {
                    dataTemporary.clear()
                    val request = ApiClient.instance.getListLeague().await()

                    var x = 0
                    while (x < request.result!!.size) {
                        if (x == 20) {
                            break
                        } else {
                            if (request.result[x].strSport?.equals("Soccer", ignoreCase = true)!!) {
                                val leagueBadge = ApiClient.instance.getLeagueDetail(
                                    request.result[x].idLeague
                                ).await()
                                dataTemporary.add(
                                    LeagueModel(
                                        idLeague = request.result[x].idLeague,
                                        strLeague = request.result[x].strLeague,
                                        strSport = request.result[x].strSport,
                                        strDescription = request.result[x].strDescription,
                                        strBadge = leagueBadge.result?.get(0)?.strBadge
                                    )
                                )
                            }
                            x++
                        }
                    }
                    val leagueData = dataTemporary as List<LeagueModel>
                    listLeague.postValue(leagueData)
                    state.postValue(LeagueState.IsLoading(false))

                } catch (networkError: IOException) {
                    Log.d(TAG, networkError.message.toString())
                    state.postValue(LeagueState.Error("Connection time out"))
                }
            }
        }
    }

    fun getListLeagues(): LiveData<List<LeagueModel>>? = listLeague

    fun getLeagueState() = state

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "MainViewModel Cleared")
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }
}

sealed class LeagueState {
    data class IsLoading(var state: Boolean = false) : LeagueState()
    data class Error(var error: String?) : LeagueState()
}