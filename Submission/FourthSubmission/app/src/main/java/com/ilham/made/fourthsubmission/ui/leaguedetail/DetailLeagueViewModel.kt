package com.ilham.made.fourthsubmission.ui.leaguedetail

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.fourthsubmission.data.repository.LeagueRepository
import com.ilham.made.fourthsubmission.model.LeagueModel

class DetailLeagueViewModel(application: Application): AndroidViewModel(application){

    private var _detailLeague = MutableLiveData<List<LeagueModel>>()
    private var _leagueId : Int = 0

    val state = LeagueRepository.getDetailLeagueState()

    fun setLeagueId(leagueId: Int){
        if (_leagueId == leagueId){
            return
        } else {
            _leagueId = leagueId
            _detailLeague.postValue(emptyList())
            _detailLeague = LeagueRepository.getDetailLeagueFromApi(leagueId)
        }
    }

    fun getDetailLeague(): LiveData<List<LeagueModel>>? = _detailLeague

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailLeagueViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailLeagueViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}