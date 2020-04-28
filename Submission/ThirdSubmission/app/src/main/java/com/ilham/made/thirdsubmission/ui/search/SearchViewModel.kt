package com.ilham.made.thirdsubmission.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.thirdsubmission.data.repository.MatchRepository
import com.ilham.made.thirdsubmission.model.MatchModel

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var _listSearchResultMatch = MutableLiveData<List<MatchModel>>()

    val searchMatchState = MatchRepository.getSearchMatchState()

    fun searchMatch(query: String) {
        _listSearchResultMatch = MatchRepository.searchMatchByTeamFromApi(query)

    }

    fun getListSearchMatch(): LiveData<List<MatchModel>>? = _listSearchResultMatch


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}