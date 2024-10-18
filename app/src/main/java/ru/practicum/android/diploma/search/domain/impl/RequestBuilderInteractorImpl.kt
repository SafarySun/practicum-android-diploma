package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderInteractorImpl(private val requestBuilderRepository: RequestBuilderRepository) :
    RequestBuilderInteractor {
    override fun setText(text: String) {
        requestBuilderRepository.setText(text)
    }

    override fun setArea(area: Area) {
        requestBuilderRepository.setArea(area)
    }

    override fun setSalary(salary: String) {
        requestBuilderRepository.setSalary(salary)
    }

    override fun setIndustry(industry: Industry) {
        requestBuilderRepository.setIndustry(industry)
    }

    override fun setCurrency(currency: String) {
        requestBuilderRepository.setCurrency(currency)
    }

    override fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderRepository.setIsShowWithSalary(isShowWithSalary)
    }

    override fun getRequest(): HashMap<String, String> {
        return requestBuilderRepository.getRequest()
    }

    override fun getSavedFilters(): SavedFilters {
        return requestBuilderRepository.getSavedFilters()
    }
}
