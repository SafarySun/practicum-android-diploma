package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.util.network.HttpStatusCode

interface FilterAreaInteractor {
    fun getCountries(): Flow<Pair<List<Area>?, HttpStatusCode?>>

}
