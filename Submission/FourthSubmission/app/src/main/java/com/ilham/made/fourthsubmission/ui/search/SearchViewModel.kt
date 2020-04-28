package com.ilham.made.fourthsubmission.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.fourthsubmission.data.repository.MatchRepository
import com.ilham.made.fourthsubmission.model.MatchModel
import com.ilham.made.fourthsubmission.utils.EspressoIdlingResource

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var _listSearchResultMatch = MutableLiveData<List<MatchModel>>()

    val searchMatchState = MatchRepository.getSearchMatchState()

    fun searchMatch(query: String) {
        EspressoIdlingResource.increment()
        _listSearchResultMatch.postValue(emptyList())
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