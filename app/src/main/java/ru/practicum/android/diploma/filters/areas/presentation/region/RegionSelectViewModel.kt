package ru.practicum.android.diploma.filters.areas.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.network.HttpStatusCode

class RegionSelectViewModel(
    private val getRegionsInteractor: FilterAreaInteractor,
    private val areaCashInteractor: AreaCashInteractor
) : ViewModel() {

    private var latestSearchText: String? = null
    private val stateLiveData = MutableLiveData<RegionSelectScreenState>()

    init {
        getAllRegions()
    }

    fun observeState(): LiveData<RegionSelectScreenState> = stateLiveData

    fun getAllRegions() {
        stateLiveData.value = RegionSelectScreenState.Loading
        viewModelScope.launch {
            getRegionsInteractor
                .getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundAreas: List<Area>?, errorMessage: HttpStatusCode?) {
        when {
            errorMessage == HttpStatusCode.NOT_CONNECTED -> {
                renderState(RegionSelectScreenState.NetworkError)
            }

            foundAreas.isNullOrEmpty() -> {
                renderState(RegionSelectScreenState.Empty)
            }

            else -> {
                if (!areaCashInteractor.getCashArea()?.parentId.isNullOrBlank()) {
                    renderState(
                        RegionSelectScreenState.ChooseItem(
                            getRegionWithCityList(foundAreas)
                        )
                    )
                } else {
                    renderState(
                        RegionSelectScreenState.ChooseItem(
                            convertToRegions(foundAreas)
                        )
                    )
                }
            }
        }
    }

    private val regionSelectDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            setFilteredRequestState(changedText)
        }

    fun searchDebounce(changedText: String) {
        if (changedText.isNotEmpty()) {
            if (latestSearchText != changedText) {
                latestSearchText = changedText
                regionSelectDebounce(changedText)
            }
        } else {
            regionSelectDebounce(DEFAULT_SEARCH_VALUE)
            getAllRegions()
        }
    }

    private fun convertToRegions(foundAreas: List<Area>): List<Area> {
        val regions = mutableListOf<Area>()
        val foundCountries = foundAreas
            .sortedBy { if (it.id == "1001") 1 else 0 }

        foundCountries.forEach { area ->
            regions.addAll(area.areas)
            area.areas.forEach { subArea ->
                val new = subArea.areas.map { it.copy(parentId = subArea.parentId) }
                regions.addAll(
                    subArea.copy(areas = new).areas
                )
            }
        }
        return regions
    }

    private fun getRegionWithCityList(areaList: List<Area>): List<Area> {
        var filteredRegionList = areaList.filter {
            it.id ==
                areaCashInteractor.getCashArea()?.parentId
        }[0].areas

        val regionsAndCities = mutableListOf<Area>()
        regionsAndCities.addAll(filteredRegionList)
        filteredRegionList.forEach { area ->

            val new = area.areas.map { it.copy(parentId = area.parentId) }
            regionsAndCities.addAll(
                area.copy(areas = new).areas
            )
        }
        return regionsAndCities
    }

    private fun renderState(state: RegionSelectScreenState) {
        stateLiveData.postValue(state)
    }

    private fun setFilteredRequestState(request: String) {
        if (request.isNotEmpty()) {
            stateLiveData.postValue(
                RegionSelectScreenState.FilterRequest(request)
            )
        }
    }

    fun finishSelect(area: Area, countryList: List<Area>) {
        val country = countryList.filter { element ->
            element.parentId == null && element.id == area.parentId
        }
        val fullArea = area.copy(parentName = country[0].name, areas = emptyList())
        areaCashInteractor.setCashArea(fullArea)
    }

    companion object {
        private const val DEFAULT_SEARCH_VALUE = ""
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
    }
}
