package ru.practicum.android.diploma.filters.industries.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filters.industries.data.dto.FilterIndustriesRequest
import ru.practicum.android.diploma.filters.industries.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.filters.industries.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient

class FilterIndustriesRepositoryImpl(
    private val networkClient: NetworkClient
) : FilterIndustriesRepository {
    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(FilterIndustriesRequest())
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val industries = (response as FilterIndustriesResponse).industries.map {
                        Industry(
                            id = it.id,
                            name = it.name,
                            industries = it.industries?.map {
                                Industry(
                                    id = it.id,
                                    name = it.name,
                                    industries = null
                                )
                            }
                        )
                    }

                    Resource.Success(getAllIndustries(industries))
                }

                else -> Resource.Error(response.resultCode)
            }
        )
    }

    private fun getAllIndustries(ind: List<Industry>): List<Industry> {
        val allIndustries = mutableListOf<Industry>()
        ind.forEach { industry ->
            allIndustries.add(industry)
            industry.industries?.let {
                allIndustries.addAll(it)
            }
        }
        return allIndustries.sortedBy { it.name }
    }
}
