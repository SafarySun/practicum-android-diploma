package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters

interface RequestBuilderInteractor {
    fun setText(text: String)
    fun setArea(area: Area)
    fun setSalary(salary: String)
    fun setIndustry(industry: Industry)
    fun setCurrency(currency: String)
    fun setIsShowWithSalary(isShowWithSalary: Boolean)
    fun cleanIndustry()
    fun cleanArea()
    fun getRequest(): HashMap<String, String>
    fun getSavedFilters(): SavedFilters
}
