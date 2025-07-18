package com.example.myweather.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.R
import com.example.myweather.search.domain.interactor.SearchInteractor
import com.example.myweather.search.domain.models.City
import com.example.myweather.search.domain.models.CityState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {
    private var searchText: String? = null

    private val stateLiveData = MutableLiveData<CityState>()
    fun observeState(): LiveData<CityState> = stateLiveData

    private var searchJob: Job? = null

    private fun search(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(CityState.Loading)
            viewModelScope.launch {
                searchInteractor.searchCity(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundCity: List<City>?, message: String?) {
        val city = mutableListOf<City>()
        if (foundCity != null) {
            city.addAll(foundCity)
        }

        when {
            message != null -> {
                renderState(
                    CityState.Error(
                        message = message
                    )
                )
            }

            city.isEmpty() -> {
                renderState(
                    CityState.Empty(
                        message = R.string.not_found
                    )
                )
            }

            else -> {
                renderState(
                    CityState.Content(
                        city = city
                    )
                )
            }
        }
    }

    private fun renderState(state: CityState) {
        stateLiveData.postValue(state)
    }

    fun searchDebounce(changedText: String) {
        if (searchText.equals(changedText)) {
            return
        }
        searchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}