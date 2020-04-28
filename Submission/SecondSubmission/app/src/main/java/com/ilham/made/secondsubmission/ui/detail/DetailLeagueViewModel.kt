package com.ilham.made.secondsubmission.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ilham.made.secondsubmission.models.LeagueModel
import com.ilham.made.secondsubmission.utils.SingleLiveEvent
import com.ilham.made.secondsubmission.webservices.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class DetailLeagueViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = DetailLeagueViewModel::class.java.simpleName
    }

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var state: SingleLiveEvent<DetailLeagueState> = SingleLiveEvent()

    private var detailLeague = MutableLiveData<List<LeagueModel>>()

    fun getDetailLeagueFromApi(leagueId: Int) {
        viewModelScope.launch {
            if (detailLeague.value == null) {
                try {
                    state.postValue(DetailLeagueState.IsLoading(true))
                    val request = ApiClient.instance.getLeagueDetail(leagueId).await()

                    detailLeague.postValue(request.result)

                    state.postValue(DetailLeagueState.IsLoading(false))

                } catch (networkError: IOException) {
                    state.postValue(DetailLeagueState.Error("Connection time out"))
                }
            }
        }
    }

    fun getDetailLeagueState() = state

    fun getDetailLeague(): LiveData<List<LeagueModel>> = detailLeague

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "DetailLeagueViewModel Cleared")
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailLeagueViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailLeagueViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }

    }
}

sealed class DetailLeagueState {
    data class IsLoading(var state: Boolean = false) : DetailLeagueState()
    data class Error(var error: String?) : DetailLeagueState()
}